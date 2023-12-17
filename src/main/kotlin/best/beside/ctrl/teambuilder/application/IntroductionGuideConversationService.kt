package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.ChatbotMessage
import best.beside.ctrl.teambuilder.domain.dto.ConversationCompleteResponse
import best.beside.ctrl.teambuilder.domain.dto.ConversationResponse
import best.beside.ctrl.teambuilder.domain.entity.Conversation
import best.beside.ctrl.teambuilder.domain.entity.ConversationMessage
import best.beside.ctrl.teambuilder.domain.repository.ConversationRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import best.beside.ctrl.teambuilder.infrastructure.ncp.service.ClovaStudioIntentDistinctionService
import org.springframework.stereotype.Service

@Service
class IntroductionGuideConversationService(
    private val userRepository: UserRepository,
    private val chatbotConversationRepository: ConversationRepository,
    private val introductionGuideService: IntroductionGuideService,
    private val introductionSummarizeService: IntroductionSummarizeService,
    private val introductionTokenizeService: IntroductionTokenizeService,
    private val intentDistinctionService: ClovaStudioIntentDistinctionService
) {
    fun listMessages(userId: Long, conversationId: Long): ConversationResponse {
        val conversation = getConversation(userId, conversationId)

        return convertToResponse(conversation)
    }

    fun startConversation(userId: Long): ConversationResponse {
        val user = userRepository.getById(userId)
        val conversation = Conversation(user)
        conversation.addBotMessage("안녕하세요.\n저는 회원님의 자기소개글 작성을 도와주는 AI \"포셔\"입니다.")
        conversation.addBotMessage("${user.name}님은 어떤분야에서 일을 하고 계신가요?")

        val savedConversation = chatbotConversationRepository.save(conversation)

        return convertToResponse(savedConversation)
    }

    fun respondConversation(userId: Long, conversationId: Long, message: String): ConversationResponse {
        val conversation = getConversation(userId, conversationId)
        val previousMessages = listPreviousMessages(conversation)

        val botResponseMessage = generateBotQuestion(previousMessages, message)

        conversation.addUserMessage(message)
        conversation.addBotMessage(botResponseMessage.content)

        val savedConversation = chatbotConversationRepository.save(conversation)

        return convertToResponse(savedConversation)
    }

    private fun generateBotQuestion(previousMessages: List<ChatbotMessage>, answer: String): ChatbotMessage.Bot {
        val lastMessage = previousMessages.last().content
        val isAppropriateAnswer = intentDistinctionService.isAppropriateAnswer(lastMessage, answer)

        return if (isAppropriateAnswer) {
            val userMessage = ChatbotMessage.User(answer)
            introductionGuideService.answer(previousMessages, userMessage)
        } else {
            ChatbotMessage.Bot("그렇군요, 다만 자기소개를 작성하기 위해서는 제가 물어본 질문에 대답을 해주셔야해요!\n$lastMessage")
        }
    }

    fun completeConversation(userId: Long, conversationId: Long): ConversationCompleteResponse {
        val conversation = getConversation(userId, conversationId)

        val totalMessages = listPreviousMessages(conversation)

        val introduction = introductionGuideService.complete(totalMessages)
        val briefIntroduction = introductionSummarizeService.summarize(introduction)
        val keywords = introductionTokenizeService.tokenize(introduction)

        return ConversationCompleteResponse(
            introduction = introduction,
            briefIntroduction = briefIntroduction,
            keywords = keywords,
        )
    }

    private fun getConversation(userId: Long, conversationId: Long): Conversation {
        val user = userRepository.getById(userId)
        return chatbotConversationRepository.getByIdAndUser(conversationId, user)
    }

    private fun listPreviousMessages(conversation: Conversation): List<ChatbotMessage> {
        return conversation.listPreviousMessages().map(::convertMessage)
    }

    private fun convertToResponse(conversation: Conversation): ConversationResponse {
        return ConversationResponse(
            id = conversation.id,
            messages = listPreviousMessages(conversation)
        )
    }

    private fun convertMessage(message: ConversationMessage): ChatbotMessage {
        return when (message.role) {
            "bot" -> ChatbotMessage.Bot(message.content)
            "user" -> ChatbotMessage.User(message.content)
            else -> throw IllegalArgumentException("Unknown role: ${message.role}")
        }
    }
}
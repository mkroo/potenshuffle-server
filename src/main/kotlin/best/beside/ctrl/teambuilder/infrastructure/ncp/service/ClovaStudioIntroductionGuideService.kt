package best.beside.ctrl.teambuilder.infrastructure.ncp.service

import best.beside.ctrl.teambuilder.application.IntroductionGuideService
import best.beside.ctrl.teambuilder.domain.dto.ChatbotMessage
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.ClovaStudioClient
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.NaverCloudPlatformProperties
import best.beside.ctrl.teambuilder.infrastructure.ncp.request.ChatCompletionRequest
import org.springframework.stereotype.Service

@Service
class ClovaStudioIntroductionGuideService(
    private val clovaStudioClient: ClovaStudioClient,
    properties: NaverCloudPlatformProperties,
    private val distinctionService: ClovaStudioIntentDistinctionService,
) : IntroductionGuideService {
    private val modelName = properties.chatCompletion.modelName
    private val apiKey = properties.chatCompletion.apiKey
    private val systemMessage = ChatCompletionRequest.SystemMessage(
        """
            - 아기에게 물어보듯이 꼼꼼히 물어봐야해
            - 자기소개글을 완성하기 위해 질문한다
            - 자기소개글에는 경력, 원하는 팀원의 모습, 본인의 강점이 포함되어야한다
            - 챗봇은 사용자에게 끊임없이 질문한다
            - 챗봇은 자기소개를 완성할때 "[자기소개 시작]" 라는 키워드를 가장 처음 문장에 붙인다
            - 챗봇은 자기소개를 완성할때 "[자기소개 끝]" 이라는 키워드를 가장 마지막에 붙인다
        """.trimIndent()
    )

    override fun answer(previousMessages: List<ChatbotMessage>, userMessage: ChatbotMessage.User): ChatbotMessage {
        val request = ChatCompletionRequest(
            messages = listOf(systemMessage) + previousMessages.plus(userMessage).map(::convert)
        )

        val response = clovaStudioClient.chatCompletion(modelName, apiKey, request)


        val introduction = extractIntroduction(response.result.message.content)

        return if (introduction != null) {
            ChatbotMessage.Complete(introduction)
        } else {
            ChatbotMessage.Bot(response.result.message.content)
        }
    }

    private fun extractIntroduction(message: String): String? {
        val firstIndex = message.lastIndexOf("[자기소개 시작]")
        val lastIndex = message.indexOf("[자기소개 끝]")

        if (firstIndex == -1) return null
        if (lastIndex == -1) return null

        return message.slice(firstIndex..lastIndex)
    }

    override fun complete(messages: List<ChatbotMessage>): String {
        val systemMessage = ChatCompletionRequest.SystemMessage("""
            - 주어진 대화의 정보를 바탕으로 자기소개를 완성합니다
            - 상냥한 말투로 자기소개를 완성합니다
            - 각 항목별로 1~2문장으로 자기소개를 완성합니다
            - 다른 얘기하지 않고 완성된 자기소개만 출력합니다
        """.trimIndent())
        val conversationMessages = messages.map(::convert)

        val guideMessage = ChatCompletionRequest.UserMessage("아래 대화내용을 바탕으로 자기소개를 만들어줘")
        val introductionMessage = ChatCompletionRequest.UserMessage(conversationMessages.map { "${it.role}: ${it.content}" }.joinToString { "\n" })

        val request = ChatCompletionRequest(
            messages = listOf(systemMessage, guideMessage, introductionMessage) + conversationMessages
        )

        val response = clovaStudioClient.chatCompletion(modelName, apiKey, request)

        return response.result.message.content
    }

    private fun convert(message: ChatbotMessage): ChatCompletionRequest.Message {
        return when (message) {
            is ChatbotMessage.Bot -> ChatCompletionRequest.AssistantMessage(message.content)
            is ChatbotMessage.User -> ChatCompletionRequest.UserMessage(message.content)
            is ChatbotMessage.Complete -> throw IllegalArgumentException("Complete message is not allowed")
        }
    }
}
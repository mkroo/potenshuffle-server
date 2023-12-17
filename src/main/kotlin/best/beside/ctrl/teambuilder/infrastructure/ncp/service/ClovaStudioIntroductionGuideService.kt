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
    private val distinctionService: ClovaStudioIntentDistinctionService
) : IntroductionGuideService {
    private val modelName = properties.chatCompletion.modelName
    private val apiKey = properties.chatCompletion.apiKey
    private val systemMessage = ChatCompletionRequest.SystemMessage(
        """
            - 아기에게 물어보듯이 꼼꼼히 물어봐야해
            - 사용자는 짧은 시간동안 서비스를 출시하기 위해 팀원들을 모집하려고해
            - 너는 사용자들이 자기소개를 완성할 수 있도록 도와주는 역할이야
            - 너는 사용자에게 질문만 할 수 있어
            - 사용자가 완성해달라고 할때까지 너는 마음대로 자기소개를 작성해서는 안돼
            
            ###
            자기소개에 필요한 항목
            - 직업 및 경력
            - 같은팀으로 일하고 싶은 사람의 성격 및 특징
            - 같은팀으로 일했을 때 본인이 보여줄 수 있는 강점
            - 프로젝트에 할애할 수 있는 시간
        """.trimIndent()
    )

    override fun answer(previousMessages: List<ChatbotMessage>, userMessage: ChatbotMessage.User): ChatbotMessage.Bot {
        val request = ChatCompletionRequest(
            messages = listOf(systemMessage) + previousMessages.plus(userMessage).map(::convert)
        )

        val response = clovaStudioClient.chatCompletion(modelName, apiKey, request)

        return ChatbotMessage.Bot(response.result.message.content)
    }

    override fun complete(messages: List<ChatbotMessage>): String {
        val systemMessage = ChatCompletionRequest.SystemMessage("주어진 대화의 정보를 바탕으로 자기소개를 완성합니다")
        val conversationMessages = messages.map(::convert)

        val request = ChatCompletionRequest(
            messages = listOf(systemMessage) + conversationMessages
        )

        val response = clovaStudioClient.chatCompletion(modelName, apiKey, request)

        return response.result.message.content
    }

    private fun convert(message: ChatbotMessage): ChatCompletionRequest.Message {
        return when (message) {
            is ChatbotMessage.Bot -> ChatCompletionRequest.AssistantMessage(message.content)
            is ChatbotMessage.User -> ChatCompletionRequest.UserMessage(message.content)
        }
    }
}
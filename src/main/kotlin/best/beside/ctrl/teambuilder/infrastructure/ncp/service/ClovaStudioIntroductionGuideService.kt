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
): IntroductionGuideService {
    private val modelName = properties.chatCompletion.modelName
    private val apiKey = properties.chatCompletion.apiKey
    private val systemMessage = ChatCompletionRequest.SystemMessage(
        """
            사용자에게 아래 항목들을 하나씩 물어봐야합니다.
            - 같은 팀으로 함께하고 싶은 사람들의 성향
            - 참여 가능한 시간
            - 직군
            - 강점
            사용자가 입력한 내용 중, 위 항목이 포함되어 있지 않다면 해당 항목을 다시 입력받을 수 있도록 질문합니다.
            위 항목들을 모두 입력받았다면 사용자에게 자기소개를 출력합니다.
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
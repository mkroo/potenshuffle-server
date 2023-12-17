package best.beside.ctrl.teambuilder.infrastructure.ncp.service

import best.beside.ctrl.teambuilder.infrastructure.ncp.config.ClovaStudioClient
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.NaverCloudPlatformProperties
import best.beside.ctrl.teambuilder.infrastructure.ncp.request.ChatCompletionRequest
import org.springframework.stereotype.Service

@Service
class ClovaStudioIntentDistinctionService(
    private val clovaStudioClient: ClovaStudioClient,
    properties: NaverCloudPlatformProperties,
) {
    private val modelName = properties.intentRecognize.modelName
    private val apiKey = properties.intentRecognize.apiKey
    private val systemMessage = ChatCompletionRequest.SystemMessage(
        """
            - 네가 물어본 질문에 의도에 알맞은 대답인지 판단한다.
            - 대답은 "예", "아니오"로만 한다.
        """.trimIndent()
    )

    fun isAppropriateAnswer(question: String, answer: String): Boolean {
        val botMessage = ChatCompletionRequest.AssistantMessage(question)
        val userMessage = ChatCompletionRequest.UserMessage(answer)

        val request = ChatCompletionRequest(
            messages = listOf(systemMessage, botMessage, userMessage),
            temperature = 0.1
        )

        val response = clovaStudioClient.chatCompletion(modelName, apiKey, request)

        return response.result.message.content == "예"
    }
}
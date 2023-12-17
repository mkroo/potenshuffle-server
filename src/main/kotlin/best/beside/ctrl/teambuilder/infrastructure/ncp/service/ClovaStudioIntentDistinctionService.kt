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
    private val modelName = properties.chatCompletion.modelName
    private val apiKey = properties.chatCompletion.apiKey
    private val systemMessage = ChatCompletionRequest.SystemMessage("네가 물어본 질문의 의도에 알맞은 대답을 했는지 '예', '아니요'로만 대답해")

    fun isAppropriateAnswer(question: String, answer: String): Boolean {
        val botMessage = ChatCompletionRequest.AssistantMessage(question)
        val userMessage = ChatCompletionRequest.UserMessage(answer)

        val request = ChatCompletionRequest(
            messages = listOf(systemMessage, botMessage, userMessage)
        )

        val response = clovaStudioClient.chatCompletion(modelName, apiKey, request)


        return response.result.message.content === "예"
    }
}
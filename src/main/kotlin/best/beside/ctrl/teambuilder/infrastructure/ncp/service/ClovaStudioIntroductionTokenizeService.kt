package best.beside.ctrl.teambuilder.infrastructure.ncp.service

import best.beside.ctrl.teambuilder.application.IntroductionTokenizeService
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.ClovaStudioClient
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.NaverCloudPlatformProperties
import best.beside.ctrl.teambuilder.infrastructure.ncp.request.ChatCompletionRequest
import org.springframework.stereotype.Service

@Service
class ClovaStudioIntroductionTokenizeService(
    private val clovaStudioClient: ClovaStudioClient,
    properties: NaverCloudPlatformProperties,
) : IntroductionTokenizeService {
    private val modelName = properties.chatCompletion.modelName
    private val apiKey = properties.chatCompletion.apiKey
    private val systemMessage = ChatCompletionRequest.SystemMessage(
        """
            - 자기소개에서 핵심 키워드를 추출합니다.
            - 키워드는 콤마로만 구분해서 출력합니다.
            - 키워드 수식어들을 출력하지 않습니다
        """.trimIndent()
    )

    override fun tokenize(introduction: String): List<String> {
        val userMessage = ChatCompletionRequest.UserMessage(content = introduction)

        val request = ChatCompletionRequest(
            messages = listOf(
                systemMessage,
                userMessage
            ),
            topP = 0.8,
            topK = 10,
            maxTokens = 256,
            temperature = 0.1,
            repeatPenalty = 0.1
        )

        val response = clovaStudioClient.chatCompletion(modelName, apiKey, request)
        return response.result.message.content.split(",").map(String::trim).filter { it.length > 1 }
    }
}
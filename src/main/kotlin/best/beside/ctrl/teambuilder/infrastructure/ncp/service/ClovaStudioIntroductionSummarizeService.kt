package best.beside.ctrl.teambuilder.infrastructure.ncp.service

import best.beside.ctrl.teambuilder.application.IntroductionSummarizeService
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.ClovaStudioClient
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.NaverCloudPlatformProperties
import best.beside.ctrl.teambuilder.infrastructure.ncp.request.SummarizationRequest
import org.springframework.stereotype.Service

@Service
class ClovaStudioIntroductionSummarizeService(
    private val clovaStudioClient: ClovaStudioClient,
    properties: NaverCloudPlatformProperties,
): IntroductionSummarizeService {
    private val appId = properties.summarization.appId
    private val apiKey = properties.summarization.apiKey

    override fun summarize(introduction: String): String {
        val request = SummarizationRequest(listOf(introduction))
        val response = clovaStudioClient.summarize(appId, apiKey, request)

        return response.result.text
    }
}
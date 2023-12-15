package best.beside.ctrl.teambuilder.infrastructure.ncp.config

import best.beside.ctrl.teambuilder.infrastructure.ncp.request.ChatCompletionRequest
import best.beside.ctrl.teambuilder.infrastructure.ncp.request.SummarizationRequest
import best.beside.ctrl.teambuilder.infrastructure.ncp.response.ChatCompletionResult
import best.beside.ctrl.teambuilder.infrastructure.ncp.response.ClovaResponse
import best.beside.ctrl.teambuilder.infrastructure.ncp.response.SummarizationResult
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

@HttpExchange
interface ClovaStudioClient {
    @PostExchange("/testapp/v1/chat-completions/{modelName}")
    fun chatCompletion(
        @PathVariable modelName: String,
        @RequestHeader("X-NCP-CLOVASTUDIO-API-KEY") apikey: String,
        @RequestBody request: ChatCompletionRequest
    ): ClovaResponse<ChatCompletionResult>

    @PostExchange("/testapp/v1/api-tools/summarization/v2/{appId}")
    fun summarize(
        @PathVariable appId: String,
        @RequestHeader("X-NCP-CLOVASTUDIO-API-KEY") apikey: String,
        @RequestBody request: SummarizationRequest
    ): ClovaResponse<SummarizationResult>
}



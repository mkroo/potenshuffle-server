package best.beside.ctrl.teambuilder.infrastructure.ncp

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

@HttpExchange
interface ClovaStudioClient {
    @PostExchange("/testapp/v1/chat-completions/HCX-002")
    fun chatCompletion(@RequestBody request: ChatCompletionRequest): ChatCompletionResponse
}
package best.beside.ctrl.teambuilder.infrastructure.ncp

data class ChatCompletionResponse(
    val status: Status,
    val result: Result
) {
    data class Status(val code: String, val message: String)
    data class Result(val message: ResultMessage)
    data class ResultMessage(val role: String, val content: String)
}

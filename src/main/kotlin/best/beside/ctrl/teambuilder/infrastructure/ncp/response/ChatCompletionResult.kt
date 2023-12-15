package best.beside.ctrl.teambuilder.infrastructure.ncp.response

data class ChatCompletionResult(val message: Message) {
    data class Message(val role: String, val content: String)
}

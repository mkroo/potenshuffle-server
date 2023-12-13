package best.beside.ctrl.teambuilder.infrastructure.ncp

data class ChatCompletionRequest(
    val messages: List<Message>,
    val topP: Double = 0.8,
    val topK: Int = 0,
    val maxTokens: Int = 256,
    val temperature: Double = 0.5,
    val repeatPenalty: Double = 5.0,
    val stopBefore: List<String> = listOf(),
    val includeAiFilters: Boolean = true,
) {
    data class Message(val role: String, val content: String)
}
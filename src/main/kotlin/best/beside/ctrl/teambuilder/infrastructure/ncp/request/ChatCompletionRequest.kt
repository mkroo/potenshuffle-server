package best.beside.ctrl.teambuilder.infrastructure.ncp.request

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
    abstract class Message(val role: String, val content: String)
    class SystemMessage(content: String) : Message("system", content)
    class UserMessage(content: String) : Message("user", content)
    class AssistantMessage(content: String) : Message("assistant", content)
}
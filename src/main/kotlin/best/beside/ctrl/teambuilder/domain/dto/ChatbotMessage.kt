package best.beside.ctrl.teambuilder.domain.dto

sealed class ChatbotMessage(val role: String, val content: String) {
    class User(content: String) : ChatbotMessage("user", content)
    class Bot(content: String) : ChatbotMessage("bot", content)
    class Complete(content: String) : ChatbotMessage("complete", content)
}
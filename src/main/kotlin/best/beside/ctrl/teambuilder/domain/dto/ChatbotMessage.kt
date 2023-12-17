package best.beside.ctrl.teambuilder.domain.dto

sealed class ChatbotMessage(val role: String) {
    data class User(val content: String) : ChatbotMessage("user")
    data class Bot(val content: String) : ChatbotMessage("bot")
}
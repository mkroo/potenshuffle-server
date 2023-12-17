package best.beside.ctrl.teambuilder.domain.dto

data class ConversationResponse(
    val id: Long,
    val messages: List<ChatbotMessage>,
    val result: ConversationCompleteResponse?
)
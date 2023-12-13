package best.beside.ctrl.teambuilder.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Conversation(
    @ManyToOne(optional = false)
    val user: User
): PrimaryKeyEntity() {
    fun listPreviousMessages(): List<ConversationMessage> {
        return messages.toList()
    }

    fun addBotMessage(content: String) {
        messages.add(ConversationMessage(this, role = "bot", content = content))
    }

    fun addUserMessage(content: String) {
        messages.add(ConversationMessage(this, role = "user", content = content))
    }

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "conversation")
    private val messages: MutableList<ConversationMessage> = mutableListOf()
}
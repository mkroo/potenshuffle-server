package best.beside.ctrl.teambuilder.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class ConversationMessage(
    @ManyToOne(optional = false)
    val conversation: Conversation,
    val role: String,
    @Column(columnDefinition = "TEXT")
    val content: String,
): PrimaryKeyEntity() {
}
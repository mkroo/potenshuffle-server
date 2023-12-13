package best.beside.ctrl.teambuilder.domain.repository

import best.beside.ctrl.teambuilder.domain.entity.Conversation
import best.beside.ctrl.teambuilder.domain.entity.User
import org.springframework.data.repository.Repository

interface ConversationRepository: Repository<Conversation, Long> {
    fun getByIdAndUser(id: Long, user: User): Conversation
    fun save(chatbotConversation: Conversation): Conversation
}
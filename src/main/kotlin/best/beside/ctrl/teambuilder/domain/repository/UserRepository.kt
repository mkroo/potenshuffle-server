package best.beside.ctrl.teambuilder.domain.repository

import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.exception.UserNotFoundException
import org.springframework.data.repository.Repository

interface UserRepository : Repository<User, Long> {
    fun getById(id: Long) = findById(id) ?: throw UserNotFoundException()
    fun findById(id: Long): User?
    fun findByEmail(email: String): User?
    fun save(user: User): User
}
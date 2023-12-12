package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.exception.UserNotFoundException
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): Account {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()

        return Account.of(user)
    }
}

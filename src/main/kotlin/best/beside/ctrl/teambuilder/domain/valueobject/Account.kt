package best.beside.ctrl.teambuilder.domain.valueobject

import best.beside.ctrl.teambuilder.domain.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class Account(
    val userId: Long,
    private val password: String,
    private val authorities: Set<GrantedAuthority> = setOf(
        SimpleGrantedAuthority("ROLE_USER")
    ),
) : UserDetails {
    companion object {
        fun of(user: User): Account {
            return Account(user.id, user.passwordHash)
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities.toMutableList()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return userId.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
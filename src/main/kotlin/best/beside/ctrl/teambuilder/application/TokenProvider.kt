package best.beside.ctrl.teambuilder.application

import org.springframework.security.core.Authentication

interface TokenProvider {
    fun createToken(authentication: Authentication): String
    fun getAuthentication(token: String): Authentication
    fun validateToken(token: String): Boolean
}
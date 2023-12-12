package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.AuthenticationResponse
import best.beside.ctrl.teambuilder.domain.dto.SignInParams
import best.beside.ctrl.teambuilder.domain.dto.SignUpParams
import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.exception.DuplicateEmailException
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val tokenProvider: TokenProvider,
) {
    fun signUp(params: SignUpParams): AuthenticationResponse {
        createUser(params)

        val jsonWebToken = getJsonWebToken(params.email, params.password)

        return AuthenticationResponse(jsonWebToken)
    }

    fun signIn(params: SignInParams): AuthenticationResponse {
        val jsonWebToken = getJsonWebToken(params.email, params.password)

        return AuthenticationResponse(jsonWebToken)
    }

    private

    fun createUser(params: SignUpParams): User {
        userRepository.findByEmail(params.email)?.let {
            throw DuplicateEmailException()
        }

        val user = User(
            email = params.email,
            name = params.name,
            password = params.password
        )

        return userRepository.save(user)
    }

    fun getJsonWebToken(email: String, password: String): String {
        val authentication = getAuthentication(email, password).let(::checkAuthentication)
        val jsonWebToken = tokenProvider.createToken(authentication)

        return jsonWebToken
    }

    fun getAuthentication(email: String, password: String): Authentication {
        return UsernamePasswordAuthenticationToken(email, password)
    }

    fun checkAuthentication(authentication: Authentication): Authentication {
        return authenticationManagerBuilder.`object`.authenticate(authentication)
    }
}
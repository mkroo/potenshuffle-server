package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.AuthenticationService
import best.beside.ctrl.teambuilder.domain.dto.AuthenticationResponse
import best.beside.ctrl.teambuilder.domain.dto.SignInParams
import best.beside.ctrl.teambuilder.domain.dto.SignUpParams
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService,
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody params: SignUpParams,
    ): ResponseEntity<AuthenticationResponse> {
        val authenticationResult = authenticationService.signUp(params)

        val httpHeaders = HttpHeaders().apply {
            add(HttpHeaders.AUTHORIZATION, "Bearer ${authenticationResult.token}")
        }

        return ResponseEntity(authenticationResult, httpHeaders, 201)
    }

    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody params: SignInParams,
    ): ResponseEntity<AuthenticationResponse> {
        val authenticationResult = authenticationService.signIn(params)

        val httpHeaders = HttpHeaders().apply {
            add(HttpHeaders.AUTHORIZATION, "Bearer ${authenticationResult.token}")
        }

        return ResponseEntity(authenticationResult, httpHeaders, 200)
    }
}
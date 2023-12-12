package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.UserService
import best.beside.ctrl.teambuilder.domain.dto.UserResponse
import best.beside.ctrl.teambuilder.domain.dto.UserUpdateParams
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/users/me")
    @SecurityRequirement(name = "JsonWebToken")
    fun getMe(
        @AuthenticationPrincipal account: Account,
    ): UserResponse {
        return userService.getUser(account.userId)
    }

    @PatchMapping("/users/me")
    @SecurityRequirement(name = "JsonWebToken")
    fun updateMe(
        @AuthenticationPrincipal account: Account,
        @RequestBody params: UserUpdateParams,
    ): UserResponse {
        return userService.updateUser(account.userId, params)
    }
}
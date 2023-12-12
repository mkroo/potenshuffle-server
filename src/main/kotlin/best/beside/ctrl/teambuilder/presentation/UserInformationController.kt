package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.UserInformationService
import best.beside.ctrl.teambuilder.domain.dto.UserInformationParams
import best.beside.ctrl.teambuilder.domain.dto.UserInformationResponse
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class UserInformationController(
    private val userInformationService: UserInformationService,
) {
    @GetMapping("/users/{userId}/information")
    fun getUserInformation(
        @PathVariable userId: Long,
    ): UserInformationResponse {
        return userInformationService.getUserInformation(userId)
    }

    @GetMapping("/users/me/information")
    @SecurityRequirement(name = "JsonWebToken")
    fun getMyInformation(
        @AuthenticationPrincipal account: Account,
    ): UserInformationResponse {
        return userInformationService.getUserInformation(account.userId)
    }

    @PatchMapping("/users/me/information")
    @SecurityRequirement(name = "JsonWebToken")
    fun writeMyInformation(
        @AuthenticationPrincipal account: Account,
        @RequestBody params: UserInformationParams,
    ): UserInformationResponse {
        return userInformationService.writeUserInformation(account.userId, params)
    }
}
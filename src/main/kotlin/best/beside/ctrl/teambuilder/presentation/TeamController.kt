package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.UserTeamService
import best.beside.ctrl.teambuilder.domain.dto.TeamResponse
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import best.beside.ctrl.teambuilder.domain.valueobject.PageResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class TeamController(
    private val userTeamService: UserTeamService,
) {
    @GetMapping("/teams")
    fun listTeams(
        @RequestParam(required = false) userId: Long?,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): PageResponse<TeamResponse> {
        val pageable = PageRequest.of(page, size)

        return userTeamService.searchTeams(userId, pageable)
    }

    @DeleteMapping("/users/me/teams")
    @SecurityRequirement(name = "JsonWebToken")
    fun leaveTeam(
        @AuthenticationPrincipal account: Account,
    ) {
        userTeamService.leaveTeam(account.userId)
    }
}
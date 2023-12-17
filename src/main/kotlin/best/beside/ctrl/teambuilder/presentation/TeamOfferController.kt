package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.TeamOfferService
import best.beside.ctrl.teambuilder.domain.dto.TeamOfferParams
import best.beside.ctrl.teambuilder.domain.dto.TeamOfferResponse
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TeamOfferController(
    private val teamOfferService: TeamOfferService,
) {
    @PostMapping("/users/me/team-offers")
    @SecurityRequirement(name = "JsonWebToken")
    fun sendTeamOffer(
        @AuthenticationPrincipal account: Account,
        @RequestBody params: TeamOfferParams,
    ): TeamOfferResponse.Sent {
        return teamOfferService.sendTeamOffer(account.userId, params)
    }
}
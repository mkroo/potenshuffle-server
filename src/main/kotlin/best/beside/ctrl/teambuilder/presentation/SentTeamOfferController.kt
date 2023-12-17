package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.SentTeamOfferService
import best.beside.ctrl.teambuilder.domain.dto.TeamOfferResponse
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import best.beside.ctrl.teambuilder.domain.valueobject.PageResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SentTeamOfferController(
    private val sentTeamOfferService: SentTeamOfferService,
) {
    @GetMapping("/users/me/sent-team-offers")
    @SecurityRequirement(name = "JsonWebToken")
    fun listSentTeamOffers(
        @AuthenticationPrincipal account: Account,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): PageResponse<TeamOfferResponse.Sent> {
        val pageable = PageRequest.of(page, size)

        return sentTeamOfferService.listSentTeamOffers(account.userId, pageable)
    }
}
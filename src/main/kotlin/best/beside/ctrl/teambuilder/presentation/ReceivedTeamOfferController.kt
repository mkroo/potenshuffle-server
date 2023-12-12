package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.ReceivedTeamOfferService
import best.beside.ctrl.teambuilder.domain.dto.ReceivedTeamOffer
import best.beside.ctrl.teambuilder.domain.type.TeamOfferResponseType
import best.beside.ctrl.teambuilder.domain.valueobject.Account
import best.beside.ctrl.teambuilder.domain.valueobject.PageResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ReceivedTeamOfferController(
    private val receivedTeamOfferService: ReceivedTeamOfferService,
) {
    @GetMapping("/users/me/received-team-offers")
    @SecurityRequirement(name = "JsonWebToken")
    fun getReceivedTeamOffers(
        @AuthenticationPrincipal account: Account,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): PageResponse<ReceivedTeamOffer> {
        val pageable = PageRequest.of(page, size)

        return receivedTeamOfferService.listReceivedTeamOffers(account.userId, pageable)
    }

    data class ReceivedJobOfferResponseParams(
        val responseType: TeamOfferResponseType,
    )

    @PostMapping("/received-team-offers/{teamOfferId}/response")
    @SecurityRequirement(name = "JsonWebToken")
    fun respondReceivedTeamOffer(
        @AuthenticationPrincipal account: Account,
        @RequestBody params: ReceivedJobOfferResponseParams,
        @PathVariable teamOfferId: Long,
    ) {
        receivedTeamOfferService.respondReceivedTeamOffer(account.userId, teamOfferId, params.responseType)
    }
}
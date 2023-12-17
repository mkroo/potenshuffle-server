package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.TeamOfferResponse
import best.beside.ctrl.teambuilder.domain.repository.TeamOfferRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import best.beside.ctrl.teambuilder.domain.valueobject.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SentTeamOfferService(
    private val userRepository: UserRepository,
    private val teamOfferRepository: TeamOfferRepository,
    private val converter: TeamOfferResponseConverter,
) {
    fun listSentTeamOffers(userId: Long, pageable: Pageable): PageResponse<TeamOfferResponse.Sent> {
        val sentUser = userRepository.getById(userId)
        val teamOfferPage = teamOfferRepository.findAllBySentUser(sentUser, pageable)
        val sentTeamOfferPage = teamOfferPage.map(converter::toSentTeamOffer)

        return PageResponse.of(sentTeamOfferPage)
    }
}
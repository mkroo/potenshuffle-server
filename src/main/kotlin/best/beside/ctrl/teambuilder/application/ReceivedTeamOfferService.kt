package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.application.eventpublisher.TeamOfferEventPublisher
import best.beside.ctrl.teambuilder.domain.dto.TeamOfferResponse
import best.beside.ctrl.teambuilder.domain.repository.TeamOfferRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import best.beside.ctrl.teambuilder.domain.type.TeamOfferResponseType
import best.beside.ctrl.teambuilder.domain.valueobject.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReceivedTeamOfferService(
    private val userRepository: UserRepository,
    private val teamOfferRepository: TeamOfferRepository,
    private val teamOfferEventPublisher: TeamOfferEventPublisher,
    private val converter: TeamOfferResponseConverter,
) {
    fun listReceivedTeamOffers(userId: Long, pageable: Pageable): PageResponse<TeamOfferResponse.Received> {
        val receivedUser = userRepository.getById(userId)
        val teamOfferPage = teamOfferRepository.findAllByReceivedUser(receivedUser, pageable)
        val receivedTeamOfferPage = teamOfferPage.map(converter::toReceivedTeamOffer)

        return PageResponse.of(receivedTeamOfferPage)
    }

    fun respondReceivedTeamOffer(userId: Long, teamOfferId: Long, responseType: TeamOfferResponseType) {
        val receivedUser = userRepository.getById(userId)
        val receivedTeamOffer = teamOfferRepository.getByIdAndReceivedUser(teamOfferId, receivedUser)

        receivedTeamOffer.response(receivedUser, responseType)
        teamOfferEventPublisher.teamOfferResponded(teamOfferId, receivedUser.id, responseType)

        teamOfferRepository.save(receivedTeamOffer)
    }
}
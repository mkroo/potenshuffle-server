package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.application.eventpublisher.TeamOfferEventPublisher
import best.beside.ctrl.teambuilder.domain.dto.ReceivedTeamOffer
import best.beside.ctrl.teambuilder.domain.entity.TeamOffer
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
) {
    fun listReceivedTeamOffers(userId: Long, pageable: Pageable): PageResponse<ReceivedTeamOffer> {
        val receivedUser = userRepository.getById(userId)
        val teamOfferPage = teamOfferRepository.findAllByReceivedUser(receivedUser, pageable)
        val receivedTeamOfferPage = teamOfferPage.map(::convertToReceivedTeamOffer)

        return PageResponse.of(receivedTeamOfferPage)
    }

    fun respondReceivedTeamOffer(userId: Long, teamOfferId: Long, responseType: TeamOfferResponseType) {
        val receivedUser = userRepository.getById(userId)
        val receivedTeamOffer = teamOfferRepository.getByIdAndReceivedUser(teamOfferId, receivedUser)

        receivedTeamOffer.response(receivedUser, responseType)
        teamOfferEventPublisher.teamOfferResponded(teamOfferId, receivedUser.id, responseType)

        teamOfferRepository.save(receivedTeamOffer)
    }

    private

    fun convertToReceivedTeamOffer(teamOffer: TeamOffer): ReceivedTeamOffer {
        return ReceivedTeamOffer(
            id = teamOffer.id,
            message = teamOffer.message,
            sentUser = ReceivedTeamOffer.SentUser(
                id = teamOffer.sentUser.id,
                name = teamOffer.sentUser.name,
                card = ReceivedTeamOffer.SentUserCard(
                    occupation = teamOffer.sentUser.information.occupation,
                    employmentStatus = teamOffer.sentUser.information.employmentStatus,
                    keywords = teamOffer.sentUser.information.keywords,
                    briefIntroduction = teamOffer.sentUser.information.briefIntroduction,
                ),
            ),
            status = teamOffer.status,
            receivedAt = teamOffer.createdAt,
        )
    }
}
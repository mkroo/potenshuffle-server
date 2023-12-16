package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.application.eventpublisher.TeamOfferEventPublisher
import best.beside.ctrl.teambuilder.domain.dto.ReceivedTeamOffer
import best.beside.ctrl.teambuilder.domain.entity.TeamOffer
import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.entity.UserInformation
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
        val teamMembers = teamOffer.sentUser.team?.teamMembers ?: emptyList()
        val teamMemberUsers = teamMembers.map { it.user }

        return ReceivedTeamOffer(
            id = teamOffer.id,
            message = teamOffer.message,
            sentUser = buildUser(teamOffer.sentUser),
            status = teamOffer.status,
            receivedAt = teamOffer.createdAt,
            sentUserTeamMembers = teamMemberUsers.map(::buildUser)
        )
    }

    private fun buildUser(user: User): ReceivedTeamOffer.User {
        return ReceivedTeamOffer.User(
            id = user.id,
            name = user.name,
            card = user.information?.let(::buildUserCard),
        )
    }

    private fun buildUserCard(userInformation: UserInformation): ReceivedTeamOffer.UserCard {
        return ReceivedTeamOffer.UserCard(
            occupation = userInformation.occupation,
            employmentStatus = userInformation.employmentStatus,
            keywords = userInformation.keywords,
            briefIntroduction = userInformation.briefIntroduction,
        )
    }
}
package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.ReceivedTeamOffer
import best.beside.ctrl.teambuilder.domain.dto.SentTeamOffer
import best.beside.ctrl.teambuilder.domain.entity.TeamOffer
import best.beside.ctrl.teambuilder.domain.repository.TeamOfferRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import best.beside.ctrl.teambuilder.domain.valueobject.PageResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SentTeamOfferService(
    private val userRepository: UserRepository,
    private val teamOfferRepository: TeamOfferRepository,
) {
    fun listSentTeamOffers(userId: Long, pageable: Pageable): PageResponse<SentTeamOffer> {
        val sentUser = userRepository.getById(userId)
        val teamOfferPage = teamOfferRepository.findAllBySentUser(sentUser, pageable)
        val sentTeamOfferPage = teamOfferPage.map(::convertToSentTeamOffer)

        return PageResponse.of(sentTeamOfferPage)
    }

    private

    fun convertToSentTeamOffer(teamOffer: TeamOffer): SentTeamOffer {
        return SentTeamOffer(
            id = teamOffer.id,
            receivedUser = SentTeamOffer.ReceivedUser(
                id = teamOffer.receivedUser.id,
                name = teamOffer.receivedUser.name,
                card = teamOffer.sentUser.information?.let {
                    SentTeamOffer.ReceivedUserCard(
                        occupation = it.occupation,
                        employmentStatus = it.employmentStatus,
                        keywords = it.keywords,
                        briefIntroduction = it.briefIntroduction,
                    )
                },
            ),
            status = teamOffer.status,
            sentAt = teamOffer.createdAt,
        )
    }
}
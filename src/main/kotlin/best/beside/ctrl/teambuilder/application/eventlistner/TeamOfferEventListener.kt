package best.beside.ctrl.teambuilder.application.eventlistner

import best.beside.ctrl.teambuilder.domain.event.TeamOfferAcceptEvent
import best.beside.ctrl.teambuilder.domain.repository.TeamOfferRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
class TeamOfferEventListener(
    private val userRepository: UserRepository,
    private val teamOfferRepository: TeamOfferRepository,
) {
    @TransactionalEventListener
    fun joinTeam(event: TeamOfferAcceptEvent) {
        val offerAcceptUser = userRepository.getById(event.respondUserId)
        val teamOffer = teamOfferRepository.getById(event.teamOfferId)
        val offerSentUser = teamOffer.sentUser

        offerSentUser.inviteTeamMember(offerAcceptUser)

        teamOfferRepository.save(teamOffer)
    }
}
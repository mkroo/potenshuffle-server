package best.beside.ctrl.teambuilder.application.eventlistner

import best.beside.ctrl.teambuilder.domain.entity.Team
import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.event.TeamOfferAcceptEvent
import best.beside.ctrl.teambuilder.domain.repository.TeamOfferRepository
import best.beside.ctrl.teambuilder.domain.repository.TeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
class TeamOfferEventListener(
    private val teamOfferRepository: TeamOfferRepository,
    private val teamRepository: TeamRepository,
) {
    @TransactionalEventListener
    fun joinTeam(event: TeamOfferAcceptEvent) {
        val teamOffer = teamOfferRepository.getById(event.teamOfferId)

        val sentUserTeam = teamOffer.sentUser.let(::getTeam)
        val receivedUserTeam = teamOffer.receivedUser.let(::getTeam)

        receivedUserTeam.merge(sentUserTeam)

        saveOrPrune(receivedUserTeam)
        saveOrPrune(sentUserTeam)
    }

    private fun getTeam(user: User): Team {
        return user.team ?: Team().apply { addMember(user) }
    }

    private fun saveOrPrune(team: Team) {
        if (team.hasEnoughMembers()) {
            teamRepository.save(team)
        } else {
            teamRepository.delete(team)
        }
    }
}
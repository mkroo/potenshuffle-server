package best.beside.ctrl.teambuilder.application.eventpublisher

import best.beside.ctrl.teambuilder.domain.entity.Team
import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.event.TeamMemberAddEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class TeamEventPublisher(
    private val publisher: ApplicationEventPublisher,
) {
    fun memberAdded(team: Team, user: User) {
        publisher.publishEvent(TeamMemberAddEvent(team.id, user.id))
    }
}
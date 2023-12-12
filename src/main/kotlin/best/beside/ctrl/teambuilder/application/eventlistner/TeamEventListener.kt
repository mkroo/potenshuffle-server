package best.beside.ctrl.teambuilder.application.eventlistner

import best.beside.ctrl.teambuilder.domain.event.TeamMemberAddEvent
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class TeamEventListener(
    private val userRepository: UserRepository,
) {
    @EventListener
    fun listen(event: TeamMemberAddEvent) {
        val user = userRepository.getById(event.userId)
        user.teamBuildingStatus = TeamBuildingStatus.IN_PROGRESS

        userRepository.save(user)
    }
}
package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.TeamResponse
import best.beside.ctrl.teambuilder.domain.dto.UserCard
import best.beside.ctrl.teambuilder.domain.entity.Team
import best.beside.ctrl.teambuilder.domain.repository.TeamRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import best.beside.ctrl.teambuilder.domain.valueobject.PageResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserTeamService(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
) {
    fun searchTeams(userId: Long?, pageable: Pageable): PageResponse<TeamResponse> {
        val teamPage = if (userId == null) {
            teamRepository.findAll(pageable)
        } else {
            val user = userRepository.getById(userId)
            val team = user.team

            if (team == null) {
                Page.empty()
            } else {
                PageImpl(listOf(team))
            }
        }

        return PageResponse.of(teamPage.map(::buildTeamResponse))
    }

    fun leaveTeam(userId: Long) {
        val user = userRepository.getById(userId)
        val team = user.team ?: return

        team.leave(user)

        if (team.hasEnoughMembers()) {
            teamRepository.save(team)
        } else {
            teamRepository.delete(team)
        }
    }

    private fun buildTeamResponse(team: Team) = TeamResponse(
        id = team.id,
        name = team.displayName,
        userCards = team.teamMembers.map { UserCard.of(it.user) }
    )
}

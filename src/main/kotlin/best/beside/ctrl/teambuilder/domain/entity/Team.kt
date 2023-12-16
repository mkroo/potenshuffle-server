package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class Team(
    val name: String,
    @OneToMany
    val teamMembers: Set<TeamMember> = setOf(),
) : PrimaryKeyEntity() {
    fun addMember(user: User) {
        teamMembers.plus(TeamMember(this, user))
        user.teamBuildingStatus = TeamBuildingStatus.IN_PROGRESS
    }
}
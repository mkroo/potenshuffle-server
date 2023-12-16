package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Transient

@Entity
class Team(
    val name: String? = null,
) : PrimaryKeyEntity() {
    @OneToMany
    val teamMembers: Set<TeamMember> = setOf()

    @get:Transient
    val displayName: String
        get() = name ?: "Team $id"

    fun addMember(user: User) {
        teamMembers.plus(TeamMember(this, user))
        user.teamBuildingStatus = TeamBuildingStatus.IN_PROGRESS
    }

    fun leave(user: User): User {
        teamMembers.minus(TeamMember(this, user))
        user.teamBuildingStatus = TeamBuildingStatus.WAITING

        return user
    }

    fun merge(otherTeam: Team) {
        val leftUsers = otherTeam.leaveAll()
        leftUsers.forEach(::addMember)
    }

    private fun leaveAll(): List<User> {
        val teamMemberUsers = teamMembers.map { it.user }

        return teamMemberUsers.map(::leave)
    }

    fun hasEnoughMembers(): Boolean {
        return teamMembers.size >= 2
    }
}
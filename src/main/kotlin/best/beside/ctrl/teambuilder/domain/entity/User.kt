package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
class User(
    val email: String,
    val name: String,
    @Transient
    val password: String,
) : PrimaryKeyEntity() {
    @Column(nullable = false)
    val passwordHash: String = BCryptPasswordEncoder().encode(password)

    @Enumerated(value = EnumType.STRING)
    var teamBuildingStatus: TeamBuildingStatus = TeamBuildingStatus.WAITING

    @OneToOne(mappedBy = "user")
    val information: UserInformation? = null

    @OneToOne
    private val teamMember: TeamMember? = null

    @get:Transient
    val team: Team?
        get() = teamMember?.team

    private fun isTeamParticipationPossible(): Boolean {
        if (teamBuildingStatus == TeamBuildingStatus.COMPLETED) return false
        if (team != null) return false

        return true
    }

    @PreUpdate
    fun updateTeamBuildingStatus() {
        teamBuildingStatus = team?.buildingStatus ?: TeamBuildingStatus.WAITING
    }
}

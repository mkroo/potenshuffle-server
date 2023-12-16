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
    @Enumerated(value = EnumType.STRING)
    var teamBuildingStatus: TeamBuildingStatus = TeamBuildingStatus.WAITING,
) : PrimaryKeyEntity() {
    @Column(nullable = false)
    val passwordHash: String = BCryptPasswordEncoder().encode(password)

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val information: UserInformation? = null

    @OneToOne
    private val teamMember: TeamMember? = null

    @get:Transient
    val team: Team?
        get() = teamMember?.team

    fun inviteTeamMember(user: User) {
        user.checkTeamParticipationPossible()

        val team = findOrCreateTeam()
        team.addMember(user)
    }

    private fun findOrCreateTeam(): Team {
        return team ?: Team("임시 팀 이름").apply { addMember(this@User) }
    }

    private fun checkTeamParticipationPossible() {
        if (isTeamParticipationPossible()) return

        throw IllegalStateException("이미 팀 빌딩 중인 유저입니다.")
    }

    private fun isTeamParticipationPossible(): Boolean {
        if (teamBuildingStatus == TeamBuildingStatus.COMPLETED) return false
        if (team != null) return false

        return true
    }
}

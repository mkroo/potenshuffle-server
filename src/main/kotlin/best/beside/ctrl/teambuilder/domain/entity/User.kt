package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.ParticipationPurpose
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

    @OneToOne
    val information: UserInformation = emptyUserInformation(this)

    private

    fun emptyUserInformation(user: User): UserInformation {
        return UserInformation(
            user = user,
            briefIntroduction = "",
            preferredTeamMember = "",
            availableParticipationTime = "",
            employmentStatus = EmploymentStatus.NONE,
            occupation = Occupation.NONE,
            participationPurpose = ParticipationPurpose.NONE,
            keywords = listOf(),
            strengths = listOf(),
            skills = listOf(),
        )
    }
}

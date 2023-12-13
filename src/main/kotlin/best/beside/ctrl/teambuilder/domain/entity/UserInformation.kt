package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.dto.UserInformationParams
import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.ParticipationPurpose
import jakarta.persistence.*

@Entity
class UserInformation(
    @OneToOne
    @JoinColumn(
        name="user_id",
        unique=true,
        nullable=false,
        updatable=false
    )
    val user: User,
    var briefIntroduction: String,
    @Column(columnDefinition = "TEXT")
    var introduction: String,
    var preferredTeamMember: String,
    var availableParticipationTime: String,
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'NONE'")
    var employmentStatus: EmploymentStatus,
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'NONE'")
    var occupation: Occupation,
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'NONE'")
    var participationPurpose: ParticipationPurpose,
    @ElementCollection(fetch = FetchType.EAGER)
    var keywords: List<String>,
    @ElementCollection(fetch = FetchType.EAGER)
    var strengths: List<String>,
    @ElementCollection(fetch = FetchType.EAGER)
    var skills: List<String>,
) : PrimaryKeyEntity() {
    fun update(params: UserInformationParams) {
        params.briefIntroduction?.let { briefIntroduction = it }
        params.introduction?.let { introduction = it }
        params.preferredTeamMember?.let { preferredTeamMember = it }
        params.availableParticipationTime?.let { availableParticipationTime = it }
        params.employmentStatus?.let { employmentStatus = it }
        params.occupation?.let { occupation = it }
        params.participationPurpose?.let { participationPurpose = it }
        params.keywords?.let { keywords = it }
        params.strengths?.let { strengths = it }
        params.skills?.let { skills = it }
    }
}
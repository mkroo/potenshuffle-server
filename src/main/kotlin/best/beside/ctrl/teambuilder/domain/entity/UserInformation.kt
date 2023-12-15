package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.dto.UserInformationParams
import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
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
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var occupation: Occupation,
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var employmentStatus: EmploymentStatus,
    @ElementCollection(fetch = FetchType.EAGER)
    var keywords: List<String>,
    @Column(columnDefinition = "TEXT")
    var briefIntroduction: String,
    @Column(columnDefinition = "TEXT")
    var introduction: String,
) : PrimaryKeyEntity() {
    fun update(params: UserInformationParams) {
        params.briefIntroduction?.let { briefIntroduction = it }
        params.introduction?.let { introduction = it }
        params.employmentStatus?.let { employmentStatus = it }
        params.occupation?.let { occupation = it }
        params.keywords?.let { keywords = it }
    }
}
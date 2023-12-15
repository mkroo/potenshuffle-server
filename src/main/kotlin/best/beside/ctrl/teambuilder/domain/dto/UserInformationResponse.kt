package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import java.time.LocalDateTime

data class UserInformationResponse(
    val user: User,
    val occupation: Occupation,
    val employmentStatus: EmploymentStatus,
    val keywords: List<String>,
    val briefIntroduction: String,
    val introduction: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    data class User(val id: Long, val name: String, val teamBuildingStatus: TeamBuildingStatus)
}
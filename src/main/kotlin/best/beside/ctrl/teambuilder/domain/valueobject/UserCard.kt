package best.beside.ctrl.teambuilder.domain.valueobject

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus

data class UserCard(
    val user: User,
    val occupation: Occupation,
    val employmentStatus: EmploymentStatus,
    val keywords: List<String>,
    val briefIntroduction: String,
) {
    data class User(val id: Long, val name: String, val teamBuildingStatus: TeamBuildingStatus)
}
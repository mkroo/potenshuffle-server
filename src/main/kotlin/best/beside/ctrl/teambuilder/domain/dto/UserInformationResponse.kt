package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import java.time.LocalDateTime

data class UserInformationResponse(
    val user: User,
    val briefIntroduction: String,
    val introduction: String,
    val occupation: Occupation,
    val keywords: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    data class User(val id: Long, val name: String, val teamBuildingStatus: TeamBuildingStatus)
}
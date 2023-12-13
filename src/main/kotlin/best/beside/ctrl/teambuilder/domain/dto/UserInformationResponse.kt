package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.ParticipationPurpose
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import java.time.LocalDateTime

data class UserInformationResponse(
    val user: User,
    val briefIntroduction: String,
    val introduction: String,
    val preferredTeamMember: String,
    val availableParticipationTime: String,
    val employmentStatus: EmploymentStatus,
    val occupation: Occupation,
    val participationPurpose: ParticipationPurpose,
    val keywords: List<String>,
    val strengths: List<String>,
    val skills: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    data class User(val id: Long, val name: String, val teamBuildingStatus: TeamBuildingStatus)
}
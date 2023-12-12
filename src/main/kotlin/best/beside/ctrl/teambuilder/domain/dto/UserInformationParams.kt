package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.ParticipationPurpose

data class UserInformationParams(
    val briefIntroduction: String?,
    val preferredTeamMember: String?,
    val availableParticipationTime: String?,
    val occupation: Occupation?,
    val participationPurpose: ParticipationPurpose?,
    val employmentStatus: EmploymentStatus?,
//    val careerExperiences: List<UserInformationResponse.CareerExperience> = listOf(),
//    val projectExperiences: List<UserInformationResponse.ProjectExperience> = listOf(),
    val keywords: List<String>?,
    val strengths: List<String>?,
    val skills: List<String>?,
)

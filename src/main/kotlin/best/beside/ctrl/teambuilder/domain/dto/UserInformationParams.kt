package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation

data class UserInformationParams(
    val briefIntroduction: String?,
    val introduction: String?,
    val occupation: Occupation?,
    val employmentStatus: EmploymentStatus?,
    val keywords: List<String>?,
)

package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus

data class UserCardSearchConditions(
    val occupations: List<Occupation>,
    val teamBuildingStatuses: List<TeamBuildingStatus>,
    val employmentStatuses: List<EmploymentStatus>,
)

package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus

data class UserUpdateParams(
    val teamBuildingStatus: TeamBuildingStatus? = null,
)
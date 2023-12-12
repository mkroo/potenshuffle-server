package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus

data class UserResponse(val id: Long, val name: String, val teamBuildingStatus: TeamBuildingStatus)

package best.beside.ctrl.teambuilder.domain.dto

data class TeamResponse(
    val id: Long,
    val name: String,
    val userCards: List<UserCard>,
)
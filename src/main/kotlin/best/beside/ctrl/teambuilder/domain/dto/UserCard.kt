package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.entity.UserInformation
import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus

data class UserCard(
    val user: User,
    val introduction: String,
    val occupation: Occupation,
    val employmentStatus: EmploymentStatus,
    val keywords: List<String>,
    val briefIntroduction: String,
) {
    data class User(val id: Long, val name: String, val teamBuildingStatus: TeamBuildingStatus)

    companion object {
        fun of(user: best.beside.ctrl.teambuilder.domain.entity.User): UserCard {
            val userInformation = user.information ?: UserInformation.empty(user)

            return UserCard(
                user = User(
                    id = user.id,
                    name = user.name,
                    teamBuildingStatus = user.teamBuildingStatus
                ),
                introduction = userInformation.introduction,
                occupation = userInformation.occupation,
                employmentStatus = userInformation.employmentStatus,
                keywords = userInformation.keywords,
                briefIntroduction = userInformation.briefIntroduction,
            )
        }
    }
}
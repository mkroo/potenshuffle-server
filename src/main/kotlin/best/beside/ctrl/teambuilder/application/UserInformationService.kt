package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.UserInformationParams
import best.beside.ctrl.teambuilder.domain.dto.UserInformationResponse
import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.entity.UserInformation
import best.beside.ctrl.teambuilder.domain.repository.UserInformationRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import org.springframework.stereotype.Service

@Service
class UserInformationService(
    private val userRepository: UserRepository,
    private val userInformationRepository: UserInformationRepository,
) {
    fun getUserInformation(userId: Long): UserInformationResponse {
        val userInformation = findOrCreateUserInformation(userId)

        return convert(userInformation)
    }

    fun writeUserInformation(userId: Long, params: UserInformationParams): UserInformationResponse {
        val userInformation = findOrCreateUserInformation(userId)

        userInformation.update(params)
        userInformationRepository.save(userInformation)

        return convert(userInformation)
    }

    private

    fun findOrCreateUserInformation(userId: Long): UserInformation {
        val user = userRepository.getById(userId)

        return user.information ?: emptyUserInformation(user)
    }

    fun convert(entity: UserInformation): UserInformationResponse {
        return UserInformationResponse(
            user = UserInformationResponse.User(
                entity.user.id,
                entity.user.name,
                entity.user.teamBuildingStatus
            ),
            occupation = entity.occupation,
            employmentStatus = entity.employmentStatus,
            keywords = entity.keywords,
            briefIntroduction = entity.briefIntroduction,
            introduction = entity.introduction,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }

    fun emptyUserInformation(user: User): UserInformation {
        return UserInformation(
            user = user,
            occupation = Occupation.NONE,
            employmentStatus = EmploymentStatus.NONE,
            keywords = listOf(),
            briefIntroduction = "",
            introduction = "",
        )
    }
}
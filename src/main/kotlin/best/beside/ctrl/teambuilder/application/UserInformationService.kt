package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.UserInformationParams
import best.beside.ctrl.teambuilder.domain.dto.UserInformationResponse
import best.beside.ctrl.teambuilder.domain.entity.UserInformation
import best.beside.ctrl.teambuilder.domain.repository.UserInformationRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
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

    fun findOrCreateUserInformation(userId: Long) = userRepository.getById(userId).information

    fun convert(entity: UserInformation): UserInformationResponse {
        return UserInformationResponse(
            user = UserInformationResponse.User(
                entity.user.id,
                entity.user.name,
                entity.user.teamBuildingStatus
            ),
            briefIntroduction = entity.briefIntroduction,
            preferredTeamMember = entity.preferredTeamMember,
            availableParticipationTime = entity.availableParticipationTime,
            employmentStatus = entity.employmentStatus,
            occupation = entity.occupation,
            participationPurpose = entity.participationPurpose,
            keywords = entity.keywords,
            strengths = entity.strengths,
            skills = entity.skills,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }
}
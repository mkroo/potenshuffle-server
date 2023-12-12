package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.UserResponse
import best.beside.ctrl.teambuilder.domain.dto.UserUpdateParams
import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUser(userId: Long): UserResponse {
        return userRepository.getById(userId).let(::convert)
    }

    fun updateUser(userId: Long, params: UserUpdateParams): UserResponse {
        val user = userRepository.getById(userId)

        params.teamBuildingStatus?.let { user.teamBuildingStatus = it }

        return userRepository.save(user).let(::convert)
    }

    private

    fun convert(user: User): UserResponse {
        return UserResponse(
            id = user.id,
            name = user.name,
            teamBuildingStatus = user.teamBuildingStatus,
        )
    }
}

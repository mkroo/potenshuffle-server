package best.beside.ctrl.teambuilder.domain.repository

import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.entity.UserInformation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.Repository

interface UserInformationRepository : Repository<UserInformation, Long> {
    fun findByUser(user: User): UserInformation?
    fun findAll(spec: Specification<UserInformation>, pageable: Pageable): Page<UserInformation>
    fun save(userInformation: UserInformation): UserInformation
}
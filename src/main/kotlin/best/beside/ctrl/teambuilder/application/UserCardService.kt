package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.UserCardSearchConditions
import best.beside.ctrl.teambuilder.domain.repository.UserInformationRepository
import best.beside.ctrl.teambuilder.domain.sepcification.UserInformationSpecifications
import best.beside.ctrl.teambuilder.domain.dto.UserCard
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class UserCardService(
    private val userInformationRepository: UserInformationRepository,
    private val specifications: UserInformationSpecifications,
) {
    fun listUserCards(searchConditions: UserCardSearchConditions, pageable: Pageable): Page<UserCard> {
        val spec = Specification.where(specifications.inOccupations(searchConditions.occupations))
            .and(specifications.inEmploymentStatuses(searchConditions.employmentStatuses))
            .and(specifications.inTeamBuildingStatuses(searchConditions.teamBuildingStatuses))

        val userInformationPage = userInformationRepository.findAll(spec, pageable)

        return userInformationPage.map { UserCard.of(it.user) }
    }
}
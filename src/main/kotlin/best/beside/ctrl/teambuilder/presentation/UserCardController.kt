package best.beside.ctrl.teambuilder.presentation

import best.beside.ctrl.teambuilder.application.UserCardService
import best.beside.ctrl.teambuilder.domain.dto.UserCardSearchConditions
import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import best.beside.ctrl.teambuilder.domain.valueobject.PageResponse
import best.beside.ctrl.teambuilder.domain.valueobject.UserCard
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserCardController(
    private val userCardService: UserCardService,
) {
    @GetMapping("/user-cards")
    fun listUserCard(
        @RequestParam(required = false, defaultValue = "") employmentStatuses: List<EmploymentStatus>,
        @RequestParam(required = false, defaultValue = "") teamBuildingStatuses: List<TeamBuildingStatus>,
        @RequestParam(required = false, defaultValue = "") occupations: List<Occupation>,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): PageResponse<UserCard> {
        val searchConditions = UserCardSearchConditions(
            employmentStatuses = employmentStatuses,
            teamBuildingStatuses = teamBuildingStatuses,
            occupations = occupations
        )
        val pageable = PageRequest.of(page, size)

        val userCardPage = userCardService.listUserCards(searchConditions, pageable)

        return PageResponse.of(userCardPage)
    }
}
package best.beside.ctrl.teambuilder.domain.sepcification

import best.beside.ctrl.teambuilder.domain.entity.UserInformation
import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class UserInformationSpecifications {
    fun inOccupations(occupations: Collection<Occupation>): Specification<UserInformation> {
        if (occupations.isEmpty()) return Specification { _, _, _ -> null }

        return Specification { root, _, _ ->
            root.get<UserInformation>("occupation").`in`(occupations)
        }
    }

    fun inEmploymentStatuses(employmentStatuses: Collection<EmploymentStatus>): Specification<UserInformation> {
        if (employmentStatuses.isEmpty()) return Specification { _, _, _ -> null }

        return Specification { root, _, _ ->
            root.get<UserInformation>("employmentStatus").`in`(employmentStatuses)
        }
    }

    fun inTeamBuildingStatuses(teamBuildingStatuses: Collection<TeamBuildingStatus>): Specification<UserInformation> {
        if (teamBuildingStatuses.isEmpty()) return Specification { _, _, _ -> null }

        return Specification { root, _, _ ->
            root.get<UserInformation>("teamBuildingStatus").`in`(teamBuildingStatuses)
        }
    }
}
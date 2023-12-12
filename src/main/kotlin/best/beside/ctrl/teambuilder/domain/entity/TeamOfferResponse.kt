package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.type.TeamOfferResponseType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class TeamOfferResponse(
    @Enumerated(value = EnumType.STRING)
    val type: TeamOfferResponseType,
) : PrimaryKeyEntity()
package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamOfferStatus
import java.time.LocalDateTime

sealed class TeamOfferResponse {
    data class Sent(
        val id: Long,
        val receivedUser: TeamOfferUser,
        val status: TeamOfferStatus,
        val sentAt: LocalDateTime,
        val receivedUserTeamMembers: List<TeamOfferUser>,
    )

    data class Received(
        val id: Long,
        val sentUser: TeamOfferUser,
        val status: TeamOfferStatus,
        val receivedAt: LocalDateTime,
        val sentUserTeamMembers: List<TeamOfferUser>,
    )

    data class TeamOfferUser(val id: Long, val name: String, val card: TeamOfferUserCard?)
    data class TeamOfferUserCard(
        val occupation: Occupation,
        val employmentStatus: EmploymentStatus,
        val keywords: List<String>,
        val briefIntroduction: String,
    )
}
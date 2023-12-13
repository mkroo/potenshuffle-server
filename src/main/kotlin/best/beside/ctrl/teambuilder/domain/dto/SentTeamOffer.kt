package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamOfferStatus
import java.time.LocalDateTime

data class SentTeamOffer(
    val id: Long,
    val receivedUser: ReceivedUser,
    val status: TeamOfferStatus,
    val sentAt: LocalDateTime,
) {
    data class ReceivedUser(val id: Long, val name: String, val card: ReceivedUserCard?)
    data class ReceivedUserCard(
        val occupation: Occupation,
        val employmentStatus: EmploymentStatus,
        val keywords: List<String>,
        val briefIntroduction: String,
    )
}
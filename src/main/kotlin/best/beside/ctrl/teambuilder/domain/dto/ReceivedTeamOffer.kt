package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamOfferStatus
import java.time.LocalDateTime

data class ReceivedTeamOffer(
    val id: Long,
    val message: String,
    val sentUser: SentUser,
    val status: TeamOfferStatus,
    val receivedAt: LocalDateTime,
) {
    data class SentUser(val id: Long, val name: String, val card: SentUserCard?)
    data class SentUserCard(
        val occupation: Occupation,
        val employmentStatus: EmploymentStatus,
        val keywords: List<String>,
        val briefIntroduction: String,
    )
}


package best.beside.ctrl.teambuilder.domain.dto

import best.beside.ctrl.teambuilder.domain.type.EmploymentStatus
import best.beside.ctrl.teambuilder.domain.type.Occupation
import best.beside.ctrl.teambuilder.domain.type.TeamOfferStatus
import java.time.LocalDateTime

data class ReceivedTeamOffer(
    val id: Long,
    val message: String,
    val sentUser: User,
    val status: TeamOfferStatus,
    val receivedAt: LocalDateTime,
    val sentUserTeamMembers: List<User>,
) {
    data class User(val id: Long, val name: String, val card: UserCard?)
    data class UserCard(
        val occupation: Occupation,
        val employmentStatus: EmploymentStatus,
        val keywords: List<String>,
        val briefIntroduction: String,
    )
}


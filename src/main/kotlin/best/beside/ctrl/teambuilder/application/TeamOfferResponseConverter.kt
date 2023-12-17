package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.TeamOfferResponse
import best.beside.ctrl.teambuilder.domain.entity.TeamOffer
import best.beside.ctrl.teambuilder.domain.entity.User
import best.beside.ctrl.teambuilder.domain.entity.UserInformation
import org.springframework.stereotype.Service

@Service
class TeamOfferResponseConverter {
    fun toSentTeamOffer(teamOffer: TeamOffer) = TeamOfferResponse.Sent(
        id = teamOffer.id,
        receivedUser = buildUser(teamOffer.receivedUser),
        status = teamOffer.status,
        sentAt = teamOffer.createdAt,
        receivedUserTeamMembers = getTeamMemberUsers(teamOffer.receivedUser).map(::buildUser)
    )

    fun toReceivedTeamOffer(teamOffer: TeamOffer) = TeamOfferResponse.Received(
        id = teamOffer.id,
        sentUser = buildUser(teamOffer.sentUser),
        status = teamOffer.status,
        receivedAt = teamOffer.createdAt,
        sentUserTeamMembers = getTeamMemberUsers(teamOffer.sentUser).map(::buildUser)
    )

    private fun getTeamMemberUsers(me: User): List<User> {
        val team = me.team ?: return emptyList()

        return team.teamMembers.map { it.user }.filter { it.id != me.id }
    }

    private fun buildUser(user: User) = TeamOfferResponse.TeamOfferUser(
        id = user.id,
        name = user.name,
        card = user.information?.let(::buildUserCard),
    )

    private fun buildUserCard(userInformation: UserInformation) = TeamOfferResponse.TeamOfferUserCard(
        occupation = userInformation.occupation,
        employmentStatus = userInformation.employmentStatus,
        keywords = userInformation.keywords,
        briefIntroduction = userInformation.briefIntroduction,
    )
}
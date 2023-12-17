package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.exception.ForbiddenException
import best.beside.ctrl.teambuilder.domain.type.TeamOfferResponseType
import best.beside.ctrl.teambuilder.domain.type.TeamOfferStatus
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class TeamOffer(
    @ManyToOne
    val sentUser: User,
    @ManyToOne
    val receivedUser: User,
) : PrimaryKeyEntity() {
    @OneToMany
    val responses: MutableList<TeamOfferResponse> = mutableListOf()

    fun response(receivedUser: User, type: TeamOfferResponseType) {
        checkIfUserReceiveOffer(receivedUser)
        responses.add(TeamOfferResponse(type))
    }

    val status: TeamOfferStatus
        get() {
            val lastResponse = responses.maxByOrNull { it.createdAt }

            if (lastResponse == null) return TeamOfferStatus.NONE

            return when (lastResponse.type) {
                TeamOfferResponseType.ACCEPT -> TeamOfferStatus.ACCEPTED
                TeamOfferResponseType.DECLINE -> TeamOfferStatus.DECLINED
            }
        }

    private

    fun checkIfUserReceiveOffer(receivedUser: User) {
        if (receivedUser != this.receivedUser) {
            throw ForbiddenException("제안을 받은 유저가 아닙니다")
        }
    }
}
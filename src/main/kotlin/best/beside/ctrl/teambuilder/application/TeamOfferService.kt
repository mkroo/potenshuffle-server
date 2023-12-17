package best.beside.ctrl.teambuilder.application

import best.beside.ctrl.teambuilder.domain.dto.TeamOfferParams
import best.beside.ctrl.teambuilder.domain.dto.TeamOfferResponse
import best.beside.ctrl.teambuilder.domain.entity.TeamOffer
import best.beside.ctrl.teambuilder.domain.repository.TeamOfferRepository
import best.beside.ctrl.teambuilder.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class TeamOfferService(
    private val userRepository: UserRepository,
    private val teamOfferRepository: TeamOfferRepository,
    private val converter: TeamOfferResponseConverter,
) {
    fun sendTeamOffer(sendUserId: Long, params: TeamOfferParams): TeamOfferResponse.Sent {
        val receiveUser = userRepository.getById(params.offerReceiveUserId)
        val sendUser = userRepository.getById(sendUserId)

        val teamOffer = TeamOffer(sentUser = sendUser, receivedUser = receiveUser)

        teamOfferRepository.save(teamOffer)

        return converter.toSentTeamOffer(teamOffer)
    }
}
package best.beside.ctrl.teambuilder.application.eventpublisher

import best.beside.ctrl.teambuilder.domain.event.TeamOfferAcceptEvent
import best.beside.ctrl.teambuilder.domain.event.TeamOfferDeclineEvent
import best.beside.ctrl.teambuilder.domain.type.TeamOfferResponseType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class TeamOfferEventPublisher(
    private val publisher: ApplicationEventPublisher,
) {
    fun teamOfferResponded(teamOfferId: Long, respondUserId: Long, responseType: TeamOfferResponseType) {
        val event: Any = when (responseType) {
            TeamOfferResponseType.ACCEPT -> TeamOfferAcceptEvent(teamOfferId, respondUserId)
            TeamOfferResponseType.DECLINE -> TeamOfferDeclineEvent(teamOfferId, respondUserId)
        }

        if (event != null) publisher.publishEvent(event)
    }
}
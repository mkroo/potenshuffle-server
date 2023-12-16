package best.beside.ctrl.teambuilder.domain.repository

import best.beside.ctrl.teambuilder.domain.entity.TeamOffer
import best.beside.ctrl.teambuilder.domain.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface TeamOfferRepository : Repository<TeamOffer, Long> {
    fun getById(id: Long): TeamOffer
    fun getByIdAndReceivedUser(id: Long, receivedUser: User): TeamOffer
    fun findAllByReceivedUser(receivedUser: User, pageable: Pageable): Page<TeamOffer>
    fun findAllBySentUser(sentUser: User, pageable: Pageable): Page<TeamOffer>
    fun save(teamOffer: TeamOffer): TeamOffer
}
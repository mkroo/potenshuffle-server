package best.beside.ctrl.teambuilder.domain.repository

import best.beside.ctrl.teambuilder.domain.entity.Team
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface TeamRepository : Repository<Team, Long> {
    fun getById(id: Long): Team
    fun findAll(pageable: Pageable): Page<Team>
    fun save(team: Team): Team
    fun delete(team: Team)
}

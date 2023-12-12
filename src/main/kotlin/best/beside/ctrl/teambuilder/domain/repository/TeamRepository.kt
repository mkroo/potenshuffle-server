package best.beside.ctrl.teambuilder.domain.repository

import best.beside.ctrl.teambuilder.domain.entity.Team
import org.springframework.data.repository.Repository

interface TeamRepository : Repository<Team, Long> {
}
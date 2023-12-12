package best.beside.ctrl.teambuilder.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
class TeamMember(
    @OneToOne
    val user: User,
    @ManyToOne
    val team: Team,
) : PrimaryKeyEntity()
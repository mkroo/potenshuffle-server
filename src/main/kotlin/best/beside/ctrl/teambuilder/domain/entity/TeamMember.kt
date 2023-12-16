package best.beside.ctrl.teambuilder.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
class TeamMember(
    @ManyToOne
    val team: Team,
    @OneToOne
    val user: User,
) : PrimaryKeyEntity()
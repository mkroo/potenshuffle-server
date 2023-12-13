package best.beside.ctrl.teambuilder.domain.entity

import best.beside.ctrl.teambuilder.domain.type.TeamBuildingStatus
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
class User(
    val email: String,
    val name: String,
    @Transient
    val password: String,
    @Enumerated(value = EnumType.STRING)
    var teamBuildingStatus: TeamBuildingStatus = TeamBuildingStatus.WAITING,
) : PrimaryKeyEntity() {
    @Column(nullable = false)
    val passwordHash: String = BCryptPasswordEncoder().encode(password)

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val information: UserInformation? = null
}

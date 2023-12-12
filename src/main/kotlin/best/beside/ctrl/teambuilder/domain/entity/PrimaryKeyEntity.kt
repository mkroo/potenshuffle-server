package best.beside.ctrl.teambuilder.domain.entity

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@MappedSuperclass
abstract class PrimaryKeyEntity : Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Transient
    private var _isNew = true

    override fun getId(): Long = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }

        return id == getIdentifier(other)
    }

    override fun hashCode() = Objects.hashCode(id)

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }

    @PrePersist
    private fun onCreate() {
        createdAt = LocalDateTime.now()
    }

    @PreUpdate
    private fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }

    private fun getIdentifier(obj: Any): Serializable {
        return if (obj is HibernateProxy) {
            (obj.hibernateLazyInitializer.identifier as Long)
        } else {
            (obj as PrimaryKeyEntity).id
        }
    }
}
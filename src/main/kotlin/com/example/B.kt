package com.example

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.*

@Entity
class EntityB(
    var name: String? = null,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "entitya_id")
    var entityA: EntityA? = null
) : PanacheEntity()

data class EntityBDto (
    val id: Long? = null,
    val name: String? = null
)

@ApplicationScoped
class Entity_B_Repository : PanacheRepository<EntityB>
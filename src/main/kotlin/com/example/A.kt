package com.example

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.*

@Entity
class EntityA (
    var smth: String? = null,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "entitya_id")
    val entBs: MutableList<EntityB>,
) : PanacheEntity()

@ApplicationScoped
class Entity_A_Repository : PanacheRepository<EntityA>

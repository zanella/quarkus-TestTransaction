package com.example

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class Service(
    private val entity_A_Repository: Entity_A_Repository,
    private val entity_B_Repository: Entity_B_Repository
) {
    @Transactional
    fun upsertContactPerson(
        entityAId: Long,
        entityBDto: EntityBDto,
    ) {
        val b = if (entityBDto.id != null) {
            entity_B_Repository
                .findById(entityBDto.id)
                ?: throw RuntimeException("No Contact Person with ID [${entityBDto.id}] was found")
        } else {
            val a = entity_A_Repository
                .findById(entityAId)
                ?: throw RuntimeException("No Account with ID [$entityAId] was found")

            EntityB(null, null).apply {
                entityA = a
            }
        }

        b.apply {
            name = entityBDto.name?.trim()
        }

        entity_B_Repository.persist(b)

        // If done explicitly it works
        /* val a = entity_A_Repository
            .findById(entityAId)
            ?: throw RuntimeException("No Account with ID [$entityAId] was found")

        a.entBs.add(b)

        entity_A_Repository.persist(a) */
    }
}
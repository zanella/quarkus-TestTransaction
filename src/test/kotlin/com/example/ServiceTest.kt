package com.example

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test

@QuarkusTest
class ServiceTest {
    @Inject
    private lateinit var entity_A_Repository: Entity_A_Repository

    @Inject
    private lateinit var entity_B_Repository: Entity_B_Repository

    //@Inject private lateinit var service: Service

    @Transactional
    fun f(a: EntityA) {
        entity_A_Repository.persistAndFlush(a)
    }

    @Test
    //@TestTransaction
    fun t0() {
        val a = EntityA(smth = "foo", mutableListOf(EntityB(name = "n_1")))

        f(a) //entity_A_Repository.persistAndFlush(a)

        entity_A_Repository.listAll().shouldBeSingleton {
            a.entBs.shouldBeSingleton {
                it.name shouldBe "n_1"
            }
        }

        entity_B_Repository.listAll().shouldBeSingleton {
            it.entityA.shouldNotBeNull().smth shouldBe "foo"
        }
    }

    /* @Test
    @TestTransaction
    fun `Add a new B` () {
        val a = EntityA(smth = "foo", mutableListOf())

        val b = EntityB(name = "n_1")

        a.entBs.add(b)

        entity_A_Repository.listAll().shouldBeEmpty()
        entity_B_Repository.listAll().shouldBeEmpty()
        //
        entity_A_Repository.persist(a)
        entity_A_Repository.listAll().shouldBeSingleton()
        entity_B_Repository.listAll().shouldBeSingleton()

        service
            .upsertContactPerson(1, EntityBDto(name = "n_2"))

        entity_A_Repository.listAll().shouldBeSingleton()
        entity_B_Repository.listAll().run {
            size shouldBe 2

            forEach {
                it.entityA!!.smth shouldBe "foo"
            }
        }

        val x = entity_A_Repository.findById(1).shouldNotBeNull()

        x.entBs.size shouldBe 2
    } */

    /* @Test
    @Transactional
    fun `Add a new B - BUT without TestTransaction` () {
        val a = EntityA(smth = "foo", mutableListOf())

        val b = EntityB(name = "n_1")

        a.entBs.add(b)

        entity_A_Repository.listAll().shouldBeEmpty()
        entity_B_Repository.listAll().shouldBeEmpty()
        //
        entity_A_Repository.persistAndFlush(a)
        entity_A_Repository.listAll().shouldBeSingleton()
        entity_B_Repository.listAll().shouldBeSingleton()

        service
            .upsertContactPerson(1, EntityBDto(name = "n_2"))

        entity_A_Repository.listAll().shouldBeSingleton()
        entity_B_Repository.listAll().run {
            size shouldBe 2

            forEach {
                it.entityA!!.smth shouldBe "foo"
            }
        }

        val x = entity_A_Repository.findById(1).shouldNotBeNull()

        x.entBs.size shouldBe 2
    } */
}

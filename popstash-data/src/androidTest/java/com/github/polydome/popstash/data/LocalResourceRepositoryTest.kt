package com.github.polydome.popstash.data

import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.data.repository.LocalResourceRepository
import com.github.polydome.popstash.data.test.createInMemoryDatabase
import com.github.polydome.popstash.domain.model.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class LocalResourceRepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var sut: LocalResourceRepository

    @Before
    fun setUp() {
        db = createInMemoryDatabase()
        sut = LocalResourceRepository(db.resourceDao())
    }

    @Test
    internal fun givenResource_whenInsert_thenEntityIsInDatabase() = runBlocking {
        val resource = Resource(
                url = "http://example.com/article"
        )

        sut.insertOne(resource)

        val entityInDatabase = db.resourceDao().findOneByUrl(resource.url)

        assertThat(entityInDatabase)
                .isEqualTo(resource.toEntity())
    }
}
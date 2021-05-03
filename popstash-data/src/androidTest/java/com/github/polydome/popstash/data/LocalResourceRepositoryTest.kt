package com.github.polydome.popstash.data

import app.cash.turbine.test
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.data.repository.LocalResourceRepository
import com.github.polydome.popstash.data.test.createInMemoryDatabase
import com.github.polydome.popstash.domain.model.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class LocalResourceRepositoryTest {
    companion object {
        val EXISTING_RESOURCE = Resource(url = "http://example.com/article")
        val NOT_EXISTING_RESOURCE = Resource(url =  "http://example.com/second-article")
    }

    private lateinit var db: AppDatabase
    private lateinit var sut: LocalResourceRepository

    @Before
    fun setUp() {
        db = createInMemoryDatabase()
        sut = LocalResourceRepository(db.resourceDao())

        db.resourceDao().insertOne(EXISTING_RESOURCE.toEntity())
    }

    @Test
    internal fun givenResourceNotInDB_whenInsert_thenEntityIsInDatabase() = runBlocking {
        sut.insertOne(NOT_EXISTING_RESOURCE)

        val entityInDatabase = db.resourceDao().findOneByUrl(NOT_EXISTING_RESOURCE.url)

        assertThat(entityInDatabase)
                .isEqualTo(NOT_EXISTING_RESOURCE.toEntity())
    }

    @Test
    internal fun givenOneResourceInDB_whenGetAllUrls_thenEmitsListWithOneUrl() = runBlocking {
        db.resourceDao().getAllUrls().test {
            assertThat(expectItem()).containsExactly(EXISTING_RESOURCE.url)
            expectNoEvents()
        }
    }

    @Test
    internal fun givenAnotherResourceIsAdded_whenGetAllUrls_thenUpdatedListIsEmitted() = runBlocking {
        db.resourceDao().getAllUrls().test {
            assertThat(expectItem()).containsExactly(EXISTING_RESOURCE.url)

            sut.insertOne(NOT_EXISTING_RESOURCE)
            assertThat(expectItem()).containsExactly(
                    EXISTING_RESOURCE.url,
                    NOT_EXISTING_RESOURCE.url
            )

            expectNoEvents()
        }
    }

    @Test
    fun givenResourceNotExists_whenWatchUrlExists_thenEmitsFalse() = runBlocking {
        sut.watchUrlExists(NOT_EXISTING_RESOURCE.url).test {
            assertThat(expectItem()).isFalse()
        }
    }

    @Test
    fun givenResourceInserted_whenWatchUrlExists_thenEmitsFalseThenTrue() = runBlocking {
        sut.watchUrlExists(NOT_EXISTING_RESOURCE.url).test {
            assertThat(expectItem()).isFalse()
            db.resourceDao().insertOne(NOT_EXISTING_RESOURCE.toEntity())
            assertThat(expectItem()).isTrue()
        }
    }
}
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

    @Test
    internal fun givenOneResourceInDB_whenGetAllUrls_thenEmitsListWithOneUrl() = runBlocking {
        val resource = Resource(
                url = "http://example.com/article"
        )

        sut.insertOne(resource)

        db.resourceDao().getAllUrls().test {
            assertThat(expectItem()).containsExactly(resource.url)
            expectNoEvents()
        }
    }

    @Test
    internal fun givenAnotherResourceIsAdded_whenGetAllUrls_thenUpdatedListIsEmitted() = runBlocking {
        val firstResource = Resource(url = "http://example.com/article")
        val secondResource = Resource(url =  "http://example.com/second-article")

        sut.insertOne(firstResource)

        db.resourceDao().getAllUrls().test {
            assertThat(expectItem()).containsExactly(firstResource.url)
            sut.insertOne(secondResource)
            assertThat(expectItem()).containsExactly(firstResource.url, secondResource.url)
            expectNoEvents()
        }
    }

    @Test
    fun givenResourceNotExists_whenWatchUrlExists_thenEmitsFalse() = runBlocking {
        val url = "http://example.com"

        sut.watchUrlExists(url).test {
            assertThat(expectItem()).isFalse()
        }
    }

    @Test
    fun givenResourceInserted_whenWatchUrlExists_thenEmitsFalseThenTrue() = runBlocking {
        val url = "http://example.com"
        val resource = Resource(url = url)

        sut.watchUrlExists(url).test {
            assertThat(expectItem()).isFalse()
            db.resourceDao().insertOne(resource.toEntity())
            assertThat(expectItem()).isTrue()
        }
    }
}
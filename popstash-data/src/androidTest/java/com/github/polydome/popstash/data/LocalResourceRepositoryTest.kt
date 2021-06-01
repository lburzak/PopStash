package com.github.polydome.popstash.data

import app.cash.turbine.test
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.data.repository.LocalResourceRepository
import com.github.polydome.popstash.data.test.createInMemoryDatabase
import com.github.polydome.popstash.domain.exception.NoSuchResourceException
import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

internal class LocalResourceRepositoryTest {
    companion object {
        val EXISTING_RESOURCE = Resource(url = "http://example.com/article")
        val NOT_EXISTING_RESOURCE = Resource(url =  "http://example.com/second-article")
        val EXISTING_METADATA = ResourceMetadata(
                title = "Final Destination",
                summary = "This is article summary.",
                site = "example.com",
                author = "Mikhail Berlioz",
        )
    }

    private lateinit var db: AppDatabase
    private lateinit var sut: LocalResourceRepository

    @Before
    fun setUp() {
        db = createInMemoryDatabase()
        sut = LocalResourceRepository(db.resourceDao())

        runBlocking {
            db.resourceDao().insertOne(EXISTING_RESOURCE.toEntity())
            db.resourceMetadataDao().insertOne(EXISTING_METADATA.toEntity(EXISTING_RESOURCE.url))
        }
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

    @Test
    fun givenResourceNotExists_whenExistsResourceById_thenReturnsTrue() = runBlocking {
        val result = sut.existsResourceByUrl(NOT_EXISTING_RESOURCE.url)
        assertThat(result).isFalse()
    }

    @Test
    fun givenResourceExists_whenExistsResourceById_thenReturnsFalse() = runBlocking {
        val result = sut.existsResourceByUrl(EXISTING_RESOURCE.url)
        assertThat(result).isTrue()
    }

    @Test
    fun givenResourceExists_whenRemoveOne_thenRemovesEntity() = runBlocking {
        sut.removeOne(EXISTING_RESOURCE.url)

        val resourceExists = db.resourceDao().existsResourceByUrl(EXISTING_RESOURCE.url)
        assertThat(resourceExists).isFalse()
    }

    @Test
    fun givenResourceExists_whenRemoveOne_thenClearsResourceMetadata(): Unit = runBlocking {
        sut.removeOne(EXISTING_RESOURCE.url)

        val metadataExists = db.resourceMetadataDao().existsByUrl(EXISTING_RESOURCE.url)
        assertThat(metadataExists).isFalse()
    }

    @Test
    fun givenResourceNotExists_whenRemoveOne_thenThrowsException() {
        assertThrows(NoSuchResourceException::class.java) {
            runBlocking { sut.removeOne(NOT_EXISTING_RESOURCE.url) }
        }
    }
}
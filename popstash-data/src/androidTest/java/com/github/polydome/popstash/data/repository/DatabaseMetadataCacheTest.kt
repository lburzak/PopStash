package com.github.polydome.popstash.data.repository

import com.github.polydome.popstash.data.AppDatabase
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.data.entity.toModel
import com.github.polydome.popstash.data.test.createInMemoryDatabase
import com.github.polydome.popstash.domain.exception.NoSuchResourceException
import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class DatabaseMetadataCacheTest {
    private lateinit var db: AppDatabase
    private lateinit var sut: DatabaseMetadataCache

    @Before
    fun setUp() {
        db = createInMemoryDatabase()
        sut = DatabaseMetadataCache(db.resourceMetadataDao(), db.resourceDao())

        runBlocking {
            db.resourceDao().insertOne(RESOURCE_CACHED.toEntity())
            db.resourceDao().insertOne(RESOURCE_NOT_CACHED.toEntity())
            db.resourceMetadataDao().insertOne(METADATA_CACHED.toEntity(URL_CACHED))
        }
    }

    @Test
    fun resourceNotInDB_put_throwsNoSuchResourceException() {
        assertThrows(NoSuchResourceException::class.java) {
            runBlocking {
                sut.put(URL_UNKNOWN, METADATA_NOT_CACHED)
            }
        }
    }

    @Test
    fun urlNotInDB_get_returnsNull() {
        runBlocking {
            val metadata = sut.get(URL_NOT_CACHED)
            assertThat(metadata).isNull()
        }
    }

    @Test
    fun urlInDB_get_returnsMetadataForUrl() {
        runBlocking {
            val metadata = sut.get(URL_CACHED)
            assertThat(metadata).isEqualTo(METADATA_CACHED)
        }
    }

    @Test
    fun urlInDB_put_updatesMetadataInDB() {
        runBlocking {
            sut.put(URL_CACHED, UPDATED_METADATA)

            val updatedMetadataInDb = db.resourceMetadataDao().findOneByUrl(URL_CACHED)
            assertThat(updatedMetadataInDb?.toModel()).isEqualTo(UPDATED_METADATA)
        }
    }

    @Test
    fun urlNotInDB_put_insertsMetadataIntoDB() {
        runBlocking {
            sut.put(URL_NOT_CACHED, METADATA_NOT_CACHED)

            val updatedMetadataInDb = db.resourceMetadataDao().findOneByUrl(URL_NOT_CACHED)
            assertThat(updatedMetadataInDb?.toModel()).isEqualTo(METADATA_NOT_CACHED)
        }
    }

    companion object {
        private const val URL_UNKNOWN = "http://example.com/robots.txt"

        private const val URL_NOT_CACHED = "http://example.com"
        private val RESOURCE_NOT_CACHED = Resource(url = URL_NOT_CACHED)
        private val METADATA_NOT_CACHED = ResourceMetadata(
                title = "Final Destination - Part 2",
                summary = "This is article summary.",
                site = "example.com",
                author = "Mikhail Berlioz"
        )

        private const val URL_CACHED = "http://example.com/mberlioz/final-destination"
        private val RESOURCE_CACHED = Resource (url = URL_CACHED)
        private val METADATA_CACHED = ResourceMetadata(
                title = "Final Destination",
                summary = "This is article summary.",
                site = "example.com",
                author = "Mikhail Berlioz"
        )

        private val UPDATED_METADATA = ResourceMetadata(
                title = "Final Destination (update)",
                summary = "This is article summary.\nUPDATE: fake news :)",
                site = "example.com",
                author = "Mikhail Berlioz"
        )
    }
}
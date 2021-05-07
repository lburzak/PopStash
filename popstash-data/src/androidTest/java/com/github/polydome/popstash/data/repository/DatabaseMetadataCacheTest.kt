package com.github.polydome.popstash.data.repository

import com.github.polydome.popstash.data.AppDatabase
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.data.entity.toModel
import com.github.polydome.popstash.data.test.createInMemoryDatabase
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DatabaseMetadataCacheTest {
    private lateinit var db: AppDatabase
    private lateinit var sut: DatabaseMetadataCache

    @Before
    fun setUp() {
        db = createInMemoryDatabase()
        sut = DatabaseMetadataCache(db.resourceMetadataDao())

        runBlocking {
            db.resourceMetadataDao().insertOne(METADATA_IN_DB.toEntity(URL_IN_DB))
        }
    }

    @Test
    fun urlNotInDB_get_returnsNull() {
        runBlocking {
            val metadata = sut.get(URL_NOT_IN_DB)
            assertThat(metadata).isNull()
        }
    }

    @Test
    fun urlInDB_get_returnsMetadataForUrl() {
        runBlocking {
            val metadata = sut.get(URL_IN_DB)
            assertThat(metadata).isEqualTo(METADATA_IN_DB)
        }
    }

    @Test
    fun urlInDB_put_updatesMetadataInDB() {
        runBlocking {
            sut.put(URL_IN_DB, UPDATED_METADATA)

            val updatedMetadataInDb = db.resourceMetadataDao().findOneByUrl(URL_IN_DB)
            assertThat(updatedMetadataInDb?.toModel()).isEqualTo(UPDATED_METADATA)
        }
    }

    @Test
    fun urlNotInDB_put_insertsMetadataIntoDB() {
        runBlocking {
            sut.put(URL_NOT_IN_DB, METADATA_NOT_IN_DB)

            val updatedMetadataInDb = db.resourceMetadataDao().findOneByUrl(URL_NOT_IN_DB)
            assertThat(updatedMetadataInDb?.toModel()).isEqualTo(METADATA_NOT_IN_DB)
        }
    }

    companion object {
        private const val URL_NOT_IN_DB = "http://example.com"
        private val METADATA_NOT_IN_DB = ResourceMetadata(
                title = "Final Destination - Part 2",
                summary = "This is article summary.",
                site = "example.com",
                author = "Mikhail Berlioz"
        )

        private const val URL_IN_DB = "http://example.com/mberlioz/final-destination"
        private val METADATA_IN_DB = ResourceMetadata(
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
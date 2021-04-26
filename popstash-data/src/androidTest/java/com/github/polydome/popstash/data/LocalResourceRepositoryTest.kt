package com.github.polydome.popstash.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.github.polydome.popstash.data.entity.ResourceEntity
import com.github.polydome.popstash.data.repository.LocalResourceRepository
import com.github.polydome.popstash.domain.model.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class LocalResourceRepositoryTest {
    val sut = LocalResourceRepository()
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val db: AppDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

    @Test
    internal fun givenResource_whenInsert_thenEntityIsInDatabase() = runBlocking {
        val resource = Resource(
                url = "http://example.com/article"
        )

        sut.insert(resource)

        val entityInDatabase = db.resourceDao().findOneByUrl(resource.url)

        assertThat(entityInDatabase)
                .isEqualTo(ResourceEntity.fromResource(resource))
    }
}
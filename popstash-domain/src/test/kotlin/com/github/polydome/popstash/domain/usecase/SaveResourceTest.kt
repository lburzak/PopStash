package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.repository.ResourceRepository
import com.github.polydome.popstash.domain.usecase.SaveResource.Error
import com.github.polydome.popstash.domain.usecase.SaveResource.Result.Failure
import com.github.polydome.popstash.domain.usecase.SaveResource.Result.Success
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class SaveResourceTest {
    val resourceRepository = mockk<ResourceRepository>(relaxUnitFun = true) {
        coEvery { existsResourceByUrl(EXISTING_RESOURCE_URL) } returns true
        coEvery { existsResourceByUrl(VALID_URL) } returns false
    }

    val sut = SaveResource(resourceRepository)

    @Nested
    inner class `given valid url` {
        @Test
        internal fun `when execute then save resource`() {
            runBlocking {
                sut.execute(VALID_URL)

                coVerify(exactly = 1) { resourceRepository.insertOne(EXPECTED_RESOURCE) }
            }
        }

        @Test
        internal fun `when execute then return success`(): Unit = runBlocking {
            val result = sut.execute(VALID_URL)

            assertThat(result).isInstanceOf(Success::class.java)
        }
    }

    @Nested
    inner class `given existing resource url` {
        @Test
        internal fun `when execute then return failure`(): Unit = runBlocking {
            val result = sut.execute(EXISTING_RESOURCE_URL)

            assertThat(result).isInstanceOf(Failure::class.java)
            assertThat((result as Failure).error).isEqualTo(Error.RESOURCE_ALREADY_EXISTS)
        }
    }

    companion object {
        const val VALID_URL = "http://example.com/article-slug"
        val EXPECTED_RESOURCE = Resource(url = VALID_URL)

        const val EXISTING_RESOURCE_URL = "http://example.com/mberlioz/final-destination"
    }
}
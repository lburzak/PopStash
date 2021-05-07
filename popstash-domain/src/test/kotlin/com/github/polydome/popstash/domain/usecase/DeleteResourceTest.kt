package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.repository.ResourceRepository
import com.github.polydome.popstash.domain.usecase.DeleteResource.Error
import com.github.polydome.popstash.domain.usecase.DeleteResource.Result.Failure
import com.github.polydome.popstash.domain.usecase.DeleteResource.Result.Success
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class DeleteResourceTest {
    private val resourceRepository: ResourceRepository = mockk(relaxUnitFun = true)
    private val sut = DeleteResource(resourceRepository)

    @Nested
    inner class `given resource not in stash` {
        init {
            coEvery { resourceRepository.existsResourceByUrl(URL) } returns false
        }

        @Test
        internal fun `when execute then return no such resource`() {
            val result = runBlocking {
                sut.execute(URL)
            }

            assertThat(result).isInstanceOf(Failure::class.java)
            assertThat((result as Failure).error).isEqualTo(Error.NO_SUCH_RESOURCE)
        }
    }

    @Nested
    inner class `given resource in stash` {
        init {
            coEvery { resourceRepository.existsResourceByUrl(URL) } returns true
        }

        @Test
        internal fun `when execute then remove resource`() {
            runBlocking {
                sut.execute(URL)
            }

            coVerify { resourceRepository.removeOne(URL) }
        }

        @Test
        internal fun `when execute then return success`() {
            val result = runBlocking {
                sut.execute(URL)
            }

            assertThat(result).isInstanceOf(Success::class.java)
        }
    }

    companion object {
        private const val URL = "http://example.com/mberlioz/final-destination"
    }
}
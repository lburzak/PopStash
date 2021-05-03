package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.repository.ResourceRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class CheckResourceExistsTest {
    companion object {
        val URL = "http://example.com"
    }

    private val resourceRepository = mockk<ResourceRepository>()
    private val sut: CheckResourceExists = CheckResourceExists(resourceRepository)

    @Nested
    inner class `given resource not exists` {
        init {
            coEvery { resourceRepository.existsResourceByUrl(URL) } returns false
        }

        @Test
        internal fun `when execute then emits false`() = runBlocking {
            val urlExists = sut.execute(URL)
            assertThat(urlExists).isFalse()
        }
    }

    @Nested
    inner class `given resource exists` {
        init {
            coEvery { resourceRepository.existsResourceByUrl(URL) } returns true
        }

        @Test
        internal fun `when execute then emits true`() = runBlocking {
            val urlExists = sut.execute(URL)
            assertThat(urlExists).isTrue()
        }
    }
}
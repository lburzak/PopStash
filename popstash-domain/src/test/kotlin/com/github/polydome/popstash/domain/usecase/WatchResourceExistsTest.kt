package com.github.polydome.popstash.domain.usecase

import app.cash.turbine.test
import com.github.polydome.popstash.domain.repository.ResourceRepository
import com.github.polydome.popstash.domain.usecase.test.hotFlowOf
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class WatchResourceExistsTest {
    companion object {
        val URL = "http://example.com"
    }

    private val resourceRepository = mockk<ResourceRepository>()
    private val sut: WatchResourceExists = WatchResourceExists(resourceRepository)

    @Nested
    inner class `given resource not exists` {
        init {
            every { resourceRepository.watchUrlExists(URL) } returns
                    hotFlowOf(false)
        }

        @Test
        internal fun `when execute then emits false`() = runBlocking {
            sut.execute(URL).test {
                assertThat(expectItem()).isFalse()
                expectNoEvents()
            }
        }
    }

    @Nested
    inner class `given resource exists` {
        init {
            every { resourceRepository.watchUrlExists(URL) } returns
                    hotFlowOf(true)
        }

        @Test
        internal fun `when execute then emits true`() = runBlocking {
            sut.execute(URL).test {
                assertThat(expectItem()).isTrue()
                expectNoEvents()
            }
        }
    }

    @Nested
    inner class `given resource created after executing` {
        private val resourceExists = hotFlowOf(false)

        init {
            every { resourceRepository.watchUrlExists(URL) } returns
                    resourceExists
        }

        @Test
        internal fun `when execute then emits false then true`() = runBlocking {
            sut.execute(URL).test {
                assertThat(expectItem()).isFalse()
                resourceExists.tryEmit(true)
                assertThat(expectItem()).isTrue()
            }
        }
    }
}
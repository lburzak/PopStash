package com.github.polydome.popstash.domain.usecase

import app.cash.turbine.test
import com.github.polydome.popstash.domain.repository.ResourceRepository
import com.github.polydome.popstash.domain.usecase.test.emptyHotFlow
import com.github.polydome.popstash.domain.usecase.test.hotFlowOf
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class ListStashedUrlsTest {
    private val resourceRepository: ResourceRepository = mockk()
    private val sut = ListStashedUrls(resourceRepository)

    @Nested
    inner class `given no url stashed` {
        init {
            every { resourceRepository.watchAllUrls() } returns
                    emptyHotFlow()
        }

        @Test
        internal fun `when execute then flow emits nothing`() = runBlocking {
            sut.execute().test {
                expectNoEvents()
            }
        }
    }

    @Nested
    inner class `given one url stashed` {
        private val stashedUrl = "http://example.com/slug"

        init {
            every { resourceRepository.watchAllUrls() } returns
                    hotFlowOf(listOf(stashedUrl))
        }

        @Test
        internal fun `when execute then emits list with one url`() = runBlocking {
            sut.execute().test {
                assertThat(expectItem()).isEqualTo(listOf(stashedUrl))
                expectNoEvents()
            }
        }
    }

    @Nested
    inner class `given url is stashed after subscribing` {
        private val stashedUrl = "http://example.com/slug"

        @Test
        internal fun `when execute then emits empty list and list with one url`() = runBlocking {
            val urls = MutableSharedFlow<List<String>>()

            every { resourceRepository.watchAllUrls() } returns urls

            sut.execute().test {
                urls.emit(emptyList())
                assertThat(expectItem()).isEmpty()

                urls.emit(listOf(stashedUrl))
                assertThat(expectItem()).containsExactly(stashedUrl)

                expectNoEvents()
            }
        }
    }
}
package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.repository.ResourceRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class SaveResourceTest {
    val sut = SaveResource()
    val resourceRepository = mockk<ResourceRepository>()

    @Nested
    inner class `given valid url` {
        private val url = "http://example.com/article-slug"

        @Test
        internal fun `when execute then save resource`() {
            runBlocking {
                sut.execute(url)

                val expectedResource = Resource(
                        url = url
                )

                verify { resourceRepository.insert(expectedResource) }
            }
        }
    }
}
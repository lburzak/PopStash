package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.model.ParsedResource
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.github.polydome.popstash.domain.service.ParserService
import com.github.polydome.popstash.domain.usecase.IdentifyResource.Result
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class IdentifyResourceTest {
    private val parserService: ParserService = mockk()
    private val sut = IdentifyResource(parserService)

    @Nested
    inner class `given url referencing parseable site` {
        init {
            every { parserService.parse(URL) } returns PARSED_RESOURCE
        }

        @Test
        fun `when execute then returns success site metadata`() = runBlocking {
            val result = sut.execute(URL)

            assertThat(result).isInstanceOf(Result.Success::class.java)

            if (result is Result.Success)
                assertThat(result.metadata).isEqualTo(PARSED_RESOURCE_METADATA)
        }
    }

    @Nested
    inner class `given url referencing unparseable site` {
        init {
            every { parserService.parse(URL) } returns null
        }

        @Test
        internal fun `when execute then returns failure`() = runBlocking {
            val result = sut.execute(URL)

            assertThat(result).isInstanceOf(Result.Failure::class.java)
        }
    }

    companion object {
        private const val URL = "http://example.com/mberlioz/final-destination"
        private val PARSED_RESOURCE_METADATA = ResourceMetadata(
                title = "Final Destination",
                summary = "This is article summary.",
                site = "example.com",
                author = "Mikhail Berlioz"
        )
        private val PARSED_RESOURCE = ParsedResource(
                metadata = PARSED_RESOURCE_METADATA
        )
    }
}
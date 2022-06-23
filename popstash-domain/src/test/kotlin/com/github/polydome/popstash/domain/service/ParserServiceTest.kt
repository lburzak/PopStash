package com.github.polydome.popstash.domain.service

import com.github.polydome.popstash.domain.model.ParsedResource
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class ParserServiceTest {
    private val parser: ResourceParser = mockk()
    private val sut = ParserService(parser)

    companion object {
        private const val RESOURCE_URL = "http://example.com/mberlioz/final-destination"
        private val PARSED_RESOURCE_METADATA = ResourceMetadata(
                title = "Final Destination",
                summary = "This is article summary.",
                site = "example.com",
                author = "Mikhail Berlioz",
                thumbnailUrl = null
        )
        private val PARSED_RESOURCE = ParsedResource(
                metadata = PARSED_RESOURCE_METADATA
        )
    }

    @Nested
    inner class `given parser returns parsed resource` {
        init {
            every { parser.parse(RESOURCE_URL) } returns PARSED_RESOURCE
        }

        @Test
        internal fun `when parse then returns parsing result`() {
            assertThat(sut.parse(RESOURCE_URL)).isEqualTo(PARSED_RESOURCE)
        }
    }

    @Nested
    inner class `given parser returns null` {
        init {
            every { parser.parse(RESOURCE_URL) } returns null
        }

        @Test
        internal fun `when parse then returns null`() {
            assertThat(sut.parse(RESOURCE_URL)).isNull()
        }
    }
}
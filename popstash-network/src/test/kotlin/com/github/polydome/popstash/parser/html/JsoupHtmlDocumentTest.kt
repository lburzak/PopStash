package com.github.polydome.popstash.parser.html

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class JsoupHtmlDocumentTest {
    @Nested
    inner class `given html contianing 2 images` {
        @Test
        internal fun `when findFirstImageUrl then returns source url of the first image`() {
            val imageUrl = JsoupHtmlDocument(HTML_2_IMAGES).findFirstImageUrl()

            assertThat(imageUrl).isEqualTo(FIRST_IMG_URL)
        }
    }

    @Nested
    inner class `given html containing no images` {
        @Test
        internal fun `when findFirstImageUrl then returns null`() {
            val imageUrl = JsoupHtmlDocument(HTML_NO_IMAGES).findFirstImageUrl()

            assertThat(imageUrl).isNull()
        }
    }

    private companion object {
        const val FIRST_IMG_URL = "http://example.com/img2.png"

        val HTML_2_IMAGES = """
                <html>
                    <body>
                        <h1>Hello World</h1>
                        <img src="$FIRST_IMG_URL">
                        <img src="http://example.com/img1.jpg">
                    </body>
                </html>
            """.trimIndent()


        val HTML_NO_IMAGES = """
                <html>
                    <body>
                        <h1>Hello World</h1>
                    </body>
                </html>
            """.trimIndent()
    }
}
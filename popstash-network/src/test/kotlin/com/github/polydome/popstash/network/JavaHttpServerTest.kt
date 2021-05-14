package com.github.polydome.popstash.network

import com.github.polydome.popstash.parser.service.HttpServer.Error
import com.github.polydome.popstash.parser.service.HttpServer.Result.Failure
import com.github.polydome.popstash.parser.service.HttpServer.Result.Success
import com.google.common.truth.Truth.assertThat
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class JavaHttpServerTest {
    private val sut = JavaHttpServer()
    private val server = MockWebServer()
    private val url: HttpUrl = server.url("/article-one")

    @AfterEach
    internal fun tearDown() {
        server.shutdown()
    }

    @Nested
    inner class `given resource exists` {
        init {
            server.enqueue(MockResponse().setBody(HTML))
        }

        @Test
        internal fun `when fetchHtml then returns success with body`() {
            val result = sut.fetchHtml(url.toString())

            assertThat(result).isInstanceOf(Success::class.java)
            assertThat((result as Success).html).isEqualTo(HTML)
        }
    }

    @Nested
    inner class `given resource not exists` {
        init {
            server.enqueue(MockResponse().setResponseCode(410))
        }

        @Test
        internal fun `when fetchHtml then returns failure with error`() {
            val result = sut.fetchHtml(url.toString())

            assertThat(result).isInstanceOf(Failure::class.java)
            assertThat((result as Failure).error).isEqualTo(Error.NO_SUCH_RESOURCE)
        }
    }

    companion object {
        const val  HTML = "<html></html>"
    }
}
package com.github.polydome.popstash.network

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class JavaDomainExtractorTest {
    private val sut = JavaDomainExtractor()

    @Test
    fun `given url starting with www returns domain`() {
        val extractedDomain = sut.extractFromUrl(URL_WWW)
        assertThat(extractedDomain).isEqualTo(DOMAIN)
    }

    @Test
    fun `given url without prefix returns domain`() {
        val extractedDomain = sut.extractFromUrl(URL_NO_PREFIX)
        assertThat(extractedDomain).isEqualTo(DOMAIN)
    }

    @Test
    fun `given url without protocol returns null`() {
        val extractedDomain = sut.extractFromUrl(URL_NO_PROTOCOL)
        assertThat(extractedDomain).isNull()
    }

    companion object {
        private const val DOMAIN = "example.com"
        private const val URL_WWW = "http://www.example.com"
        private const val URL_NO_PREFIX = "http://example.com"
        private const val URL_NO_PROTOCOL = "www.example.com"
    }
}
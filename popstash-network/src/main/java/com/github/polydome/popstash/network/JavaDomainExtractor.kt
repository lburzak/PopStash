package com.github.polydome.popstash.network

import com.github.polydome.popstash.parser.service.DomainExtractor
import java.net.URI

class JavaDomainExtractor : DomainExtractor {
    override fun extractFromUrl(url: String): String? {
        val uri = URI(url)
        val domain: String = uri.host ?: return null

        return if (domain.hasWWWPrefix()) domain.dropPrefix() else domain
    }

    private fun String.hasWWWPrefix() =
            this.startsWith("www.")

    private fun String.dropPrefix() =
            this.drop(4)
}
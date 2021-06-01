package com.github.polydome.popstash.parser.readability

import com.github.polydome.popstash.domain.model.ParsedResource
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.github.polydome.popstash.domain.service.ResourceParser
import com.github.polydome.popstash.parser.service.DomainExtractor
import com.github.polydome.popstash.parser.service.HttpServer
import com.github.polydome.popstash.parser.service.HttpServer.Result.Success
import net.dankito.readability4j.Readability4J
import javax.inject.Inject

class ReadabilityParser @Inject constructor(
        private val readabilityFactory: ReadabilityFactory,
        private val domainExtractor: DomainExtractor,
        private val httpServer: HttpServer,
) : ResourceParser {
    override fun parse(url: String): ParsedResource? {
        val html = when (val result = httpServer.fetchHtml(url)) {
            is Success -> result.html
            else -> return null
        }

        val readability = readabilityFactory.forUri(url, html)
        val article = readability.parseSafely()
        val domain = domainExtractor.extractFromUrl(url)

        val metadata = ResourceMetadata(
                title = article?.title ?: url,
                summary = article?.excerpt ?: "",
                site = domain ?: "",
                author = article?.byline ?: "",
        )

        return ParsedResource(metadata = metadata)
    }

    private fun Readability4J.parseSafely() =
            try { parse() } catch (e: Exception) { null }
}
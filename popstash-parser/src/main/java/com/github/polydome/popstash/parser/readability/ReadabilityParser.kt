package com.github.polydome.popstash.parser.readability

import com.github.polydome.popstash.domain.model.ParsedResource
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.github.polydome.popstash.domain.service.ResourceParser
import com.github.polydome.popstash.parser.service.DomainExtractor
import com.github.polydome.popstash.parser.service.HttpServer
import com.github.polydome.popstash.parser.service.HttpServer.Result.Success
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

        val article = readabilityFactory.forUri(url, html).parse()
        val domain = domainExtractor.extractFromUrl(article.uri)

        val metadata = ResourceMetadata(
                title = article.title ?: "Untitled",
                summary = article.excerpt ?: "",
                site = domain ?: "",
                author = article.byline
        )

        return ParsedResource(metadata = metadata)
    }
}
package com.github.polydome.popstash.parser.readability

import com.github.polydome.popstash.parser.service.HttpServer
import net.dankito.readability4j.Readability4J

class ReadabilityFactory(private val httpServer: HttpServer) {
    fun forUri(uri: String): Readability4J {
        val html = httpServer.fetchHtml(uri)
        return Readability4J(uri, html)
    }
}
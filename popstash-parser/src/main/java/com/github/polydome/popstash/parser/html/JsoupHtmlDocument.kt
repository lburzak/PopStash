package com.github.polydome.popstash.parser.html

import com.github.polydome.popstash.parser.service.HtmlDocument
import org.jsoup.Jsoup

class JsoupHtmlDocument(html: String) : HtmlDocument {
    private val document = Jsoup.parse(html)

    override fun findFirstImageUrl(): String? {
        return document
                .select("img")
                .first()
                ?.attr("src")
    }

    class Factory : HtmlDocument.Factory {
        override fun createFrom(html: String): HtmlDocument {
            return JsoupHtmlDocument(html)
        }
    }
}
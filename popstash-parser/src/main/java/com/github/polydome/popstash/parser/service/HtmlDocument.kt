package com.github.polydome.popstash.parser.service

interface HtmlDocument {
    fun findFirstImageUrl(): String?

    interface Factory {
        fun createFrom(html: String): HtmlDocument
    }
}
package com.github.polydome.popstash.parser.service

interface DomainExtractor {
    fun extractFromUrl(url: String): String?
}
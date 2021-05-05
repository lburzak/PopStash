package com.github.polydome.popstash.domain.service

import com.github.polydome.popstash.domain.model.ParsedResource

class ParserService(private val parser: ResourceParser) {
    fun parse(url: String): ParsedResource? {
        return parser.parse(url)
    }
}
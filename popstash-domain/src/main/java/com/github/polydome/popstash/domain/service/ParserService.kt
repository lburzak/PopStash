package com.github.polydome.popstash.domain.service

import com.github.polydome.popstash.domain.model.ParsedResource
import javax.inject.Inject

class ParserService @Inject constructor(private val parser: ResourceParser) {
    fun parse(url: String): ParsedResource? {
        return parser.parse(url)
    }
}
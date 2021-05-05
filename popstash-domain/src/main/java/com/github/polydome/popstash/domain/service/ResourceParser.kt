package com.github.polydome.popstash.domain.service

import com.github.polydome.popstash.domain.model.ParsedResource

interface ResourceParser {
    fun parse(url: String): ParsedResource?
}
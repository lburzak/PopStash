package com.github.polydome.popstash.app.presentation.service

interface PatternMatcher {
    fun matchUrl(text: String): Boolean
}
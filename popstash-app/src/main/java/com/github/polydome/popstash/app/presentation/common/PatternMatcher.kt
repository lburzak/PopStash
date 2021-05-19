package com.github.polydome.popstash.app.presentation.common

interface PatternMatcher {
    fun matchUrl(text: String): Boolean
}
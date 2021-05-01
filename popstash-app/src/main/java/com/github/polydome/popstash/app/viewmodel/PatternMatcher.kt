package com.github.polydome.popstash.app.viewmodel

interface PatternMatcher {
    fun matchUrl(text: String): Boolean
}
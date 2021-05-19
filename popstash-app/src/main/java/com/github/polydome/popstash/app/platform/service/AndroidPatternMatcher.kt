package com.github.polydome.popstash.app.platform.service

import com.github.polydome.popstash.app.presentation.common.PatternMatcher
import com.github.polydome.popstash.domain.service.URLValidator
import javax.inject.Inject

class AndroidPatternMatcher @Inject constructor() : PatternMatcher, URLValidator {
    override fun matchUrl(text: String): Boolean =
            android.util.Patterns.WEB_URL.matcher(text).matches()

    override fun validateUrl(url: String): Boolean =
            matchUrl(url)
}
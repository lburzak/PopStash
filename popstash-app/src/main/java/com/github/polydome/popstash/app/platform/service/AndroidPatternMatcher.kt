package com.github.polydome.popstash.app.platform.service

import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import javax.inject.Inject

class AndroidPatternMatcher @Inject constructor() : PatternMatcher {
    override fun matchUrl(text: String): Boolean =
            android.util.Patterns.WEB_URL.matcher(text).matches()
}
package com.github.polydome.popstash.app.service

import com.github.polydome.popstash.app.viewmodel.PatternMatcher
import javax.inject.Inject

class AndroidPatternMatcher @Inject constructor() : PatternMatcher {
    override fun matchUrl(text: String): Boolean =
            android.util.Patterns.WEB_URL.matcher(text).matches()
}
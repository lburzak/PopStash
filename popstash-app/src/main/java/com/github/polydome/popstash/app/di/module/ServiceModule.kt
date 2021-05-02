package com.github.polydome.popstash.app.di.module

import com.github.polydome.popstash.app.platform.service.AndroidClipboard
import com.github.polydome.popstash.app.platform.service.AndroidPatternMatcher
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun clipboard(androidClipboard: AndroidClipboard): Clipboard

    @Binds
    abstract fun patternMatcher(androidPatternMatcher: AndroidPatternMatcher): PatternMatcher
}
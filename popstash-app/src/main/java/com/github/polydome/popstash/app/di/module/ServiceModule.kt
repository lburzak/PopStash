package com.github.polydome.popstash.app.di.module

import com.github.polydome.popstash.app.service.AndroidClipboard
import com.github.polydome.popstash.app.service.AndroidPatternMatcher
import com.github.polydome.popstash.app.viewmodel.Clipboard
import com.github.polydome.popstash.app.viewmodel.PatternMatcher
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
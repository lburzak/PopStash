package com.github.polydome.popstash.app.di.module

import com.github.polydome.popstash.app.platform.service.*
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class ServiceModule {
    @Binds
    @ActivityScoped
    abstract fun clipboard(androidClipboard: AndroidClipboard): Clipboard

    @Binds
    @ActivityScoped
    abstract fun patternMatcher(androidPatternMatcher: AndroidPatternMatcher): PatternMatcher

    @Binds
    @ActivityScoped
    abstract fun windowEventEmitter(windowEventBus: WindowEventBus): WindowEventEmitter

    @Binds
    @ActivityScoped
    abstract fun windowEventListener(windowEventBus: WindowEventBus): WindowEventListener
}
package com.github.polydome.popstash.app.di.entrypoint

import com.github.polydome.popstash.app.platform.service.SettingsManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ThemeProviderEntryPoint {
    fun themeProvider(): SettingsManager
}
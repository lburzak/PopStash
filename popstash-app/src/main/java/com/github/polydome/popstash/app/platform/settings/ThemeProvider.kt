package com.github.polydome.popstash.app.platform.settings

import androidx.annotation.StyleRes
import kotlinx.coroutines.flow.Flow

interface ThemeProvider {
    @StyleRes
    fun getThemeResId(): Int

    val themeChanges: Flow<Int>
}
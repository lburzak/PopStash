package com.github.polydome.popstash.app.platform.service

import android.content.SharedPreferences
import javax.inject.Inject

class SettingsManager @Inject constructor(private val preferences: SharedPreferences) : Settings, ThemeProvider {
    override var theme: Settings.Theme
        get() = Settings.Theme.ofKey(preferences.getInt(KEY_THEME, 0))
        set(value) { preferences.edit().putInt(KEY_THEME, value.ordinal).apply() }

    override fun getThemeResId(): Int {
        return theme.resId
    }

    companion object {
        const val KEY_THEME = "PREFERENCE_KEY_THEME"
    }
}
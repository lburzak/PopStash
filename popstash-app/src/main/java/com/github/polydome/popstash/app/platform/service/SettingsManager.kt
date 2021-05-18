package com.github.polydome.popstash.app.platform.service

import android.content.SharedPreferences
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@ActivityScoped
class SettingsManager @Inject constructor(private val preferences: SharedPreferences) : Settings, ThemeProvider {
    override suspend fun setTheme(theme: Settings.Theme) {
        persistThemeKey(theme.key)
        _themeResId.emit(theme.resId)
    }

    private val _themeResId: MutableSharedFlow<Int> = MutableSharedFlow(replay = 0)

    private val theme: Settings.Theme
        get() = Settings.Theme.ofKey(getPersistedThemeKey())

    override fun getThemeResId(): Int = theme.resId

    override val themeChanges: Flow<Int> =  _themeResId

    private fun getPersistedThemeKey(): Int =
            preferences.getInt(KEY_THEME, Settings.Theme.default.key)

    private fun persistThemeKey(key: Int) {
        preferences.edit().putInt(KEY_THEME, key).apply()
    }

    companion object {
        const val KEY_THEME = "PREFERENCE_KEY_THEME"
    }
}
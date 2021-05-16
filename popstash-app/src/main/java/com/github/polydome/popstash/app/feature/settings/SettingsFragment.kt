package com.github.polydome.popstash.app.feature.settings

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.platform.service.Settings
import com.github.polydome.popstash.app.platform.service.Settings.Theme
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Move to platform
class SettingsFragment @Inject constructor(private val settings: Settings) : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themePreference = findPreference<ListPreference>("theme")

        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            onThemeChange(preference as ListPreference, newValue as String)
            true
        }
    }

    private fun onThemeChange(themePreference: ListPreference, themeKey: String) {
        val theme = themePreference.parseThemeValue(themeKey)

        lifecycleScope.launch {
            settings.setTheme(theme)
        }
    }

    private fun ListPreference.parseThemeValue(value: String): Theme {
        val valueIndex = findIndexOfValue(value)
        return Theme.ofKey(valueIndex)
    }
}
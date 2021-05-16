package com.github.polydome.popstash.app.feature.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.platform.service.Settings
import com.github.polydome.popstash.app.platform.service.Settings.Theme
import javax.inject.Inject

class SettingsFragment @Inject constructor(private val settings: Settings) : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themePreference = findPreference<ListPreference>("theme")

        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            settings.theme = themePreference.parseThemeValue(newValue)
            true
        }
    }

    private fun ListPreference.parseThemeValue(value: Any): Theme {
        val valueIndex = findIndexOfValue(value as String?)
        return Theme.ofKey(valueIndex)
    }
}
package com.github.polydome.popstash.app.feature.settings

import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.github.polydome.popstash.app.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themePreference = findPreference<ListPreference>("theme")

        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val theme = themePreference.parseThemeValue(newValue)
            onThemeChange(theme)

            true
        }
    }

    private fun onThemeChange(theme: Theme) {
        val activity = requireActivity()
        activity.setTheme(theme.resId)
    }

    private fun ListPreference.parseThemeValue(value: Any): Theme {
        val valueIndex = findIndexOfValue(value as String?)
        return Theme.values()[valueIndex]
    }

    private enum class Theme(@StyleRes val resId: Int) {
        LIGHT(R.style.AppTheme),
        DARK(R.style.AppTheme_Dark)
    }
}
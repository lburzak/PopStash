package com.github.polydome.popstash.app.platform.settings

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.platform.service.Settings
import com.github.polydome.popstash.app.platform.service.Settings.Theme
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsFragment @Inject constructor(
        private val settings: Settings,
) : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val preferences = Preferences()

        with(preferences) {
            theme.setOnPreferenceChangeListener { preference, newValue ->
                onThemeChange(preference as ListPreference, newValue as String)
                true
            }

            about.setOnPreferenceClickListener {
                showLicensesPage()
                true
            }
        }
    }

    private fun onThemeChange(themePreference: ListPreference, themeKey: String) {
        val theme = themePreference.parseThemeValue(themeKey)

        lifecycleScope.launch {
            settings.setTheme(theme)
        }
    }

    private fun showLicensesPage() {
        findNavController().navigate(R.id.destination_about)
    }

    private fun ListPreference.parseThemeValue(value: String): Theme {
        val valueIndex = findIndexOfValue(value)
        return Theme.ofKey(valueIndex)
    }

    private fun <T: Preference> requirePreference(key: CharSequence): T =
            findPreference(key) ?:
                throw IllegalArgumentException("No such preference registered: $key")

    private inner class Preferences(
            val theme: ListPreference = requirePreference("theme"),
            val about: Preference = requirePreference("about")
    )
}
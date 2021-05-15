package com.github.polydome.popstash.app.feature.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.github.polydome.popstash.app.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
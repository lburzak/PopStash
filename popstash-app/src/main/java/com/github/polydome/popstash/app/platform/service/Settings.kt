package com.github.polydome.popstash.app.platform.service

import androidx.annotation.StyleRes
import com.github.polydome.popstash.app.R

interface Settings {
    suspend fun setTheme(theme: Theme)

    enum class Theme(@StyleRes val resId: Int) {
        LIGHT(R.style.AppTheme),
        DARK(R.style.AppTheme_Dark);

        val key: Int
            get() = ordinal

        companion object {
            fun ofKey(key: Int): Theme = values()[key]
        }
    }
}
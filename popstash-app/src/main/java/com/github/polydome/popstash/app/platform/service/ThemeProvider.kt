package com.github.polydome.popstash.app.platform.service

import androidx.annotation.StyleRes

interface ThemeProvider {
    @StyleRes fun getThemeResId(): Int
}
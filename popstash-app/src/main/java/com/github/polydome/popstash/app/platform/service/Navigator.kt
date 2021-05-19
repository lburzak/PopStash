package com.github.polydome.popstash.app.platform.service

import androidx.annotation.IdRes

interface Navigator {
    fun navigateTo(@IdRes destinationId: Int)
}
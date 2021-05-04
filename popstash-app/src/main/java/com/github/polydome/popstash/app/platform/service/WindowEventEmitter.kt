package com.github.polydome.popstash.app.platform.service

import kotlinx.coroutines.flow.Flow

interface WindowEventEmitter {
    val focusChanges: Flow<Boolean>
}
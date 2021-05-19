package com.github.polydome.popstash.app.platform.event

import kotlinx.coroutines.flow.Flow

interface WindowEventEmitter {
    val focusChanges: Flow<Boolean>
}
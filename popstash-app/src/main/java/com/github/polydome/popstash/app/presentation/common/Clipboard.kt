package com.github.polydome.popstash.app.presentation.common

import kotlinx.coroutines.flow.Flow

interface Clipboard {
    fun getText(): String?
    fun contents(): Flow<String>
}
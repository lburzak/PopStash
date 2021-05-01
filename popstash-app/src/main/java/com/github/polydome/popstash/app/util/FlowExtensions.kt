package com.github.polydome.popstash.app.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun <T> intervalFlow(periodMillis: Long, producer: () -> T) = flow {
    while (true) {
        emit(producer())
        delay(periodMillis)
    }
}
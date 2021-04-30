package com.github.polydome.popstash.domain.usecase.test

import kotlinx.coroutines.flow.MutableSharedFlow


fun <T> hotFlowOf(value: T) =
        MutableSharedFlow<T>(1)
                .apply {
                    tryEmit(value)
                }

fun <T> emptyHotFlow() =
        MutableSharedFlow<T>()
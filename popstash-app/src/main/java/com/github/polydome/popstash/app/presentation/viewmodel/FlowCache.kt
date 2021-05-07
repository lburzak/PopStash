package com.github.polydome.popstash.app.presentation.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class FlowCache <T> private constructor(private val flow: Flow<T>) {
    companion object {
        fun <T> of(flow: Flow<T>): FlowCache<T> {
            return FlowCache(flow)
        }
    }

    fun cacheIn(coroutineScope: CoroutineScope): FlowCache<T> {
        coroutineScope.launch {
            flow.collect {
                _last = it
            }
        }

        return this
    }

    private var _last: T? = null

    val last: T
        get() = _last ?: throw IllegalStateException("Flow is empty")
}
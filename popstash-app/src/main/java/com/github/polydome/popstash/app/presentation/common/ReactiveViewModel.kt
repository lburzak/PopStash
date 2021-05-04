package com.github.polydome.popstash.app.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class ReactiveViewModel<C> : ViewModel() {
    private val _commands = MutableSharedFlow<C>()
    protected val commands = _commands.asSharedFlow()

    fun command(command: C) {
        viewModelScope.launch { _commands.emit(command) }
    }
}
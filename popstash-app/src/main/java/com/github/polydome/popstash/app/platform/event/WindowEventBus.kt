package com.github.polydome.popstash.app.platform.event

import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WindowEventBus @Inject constructor(
        @ActivityScoped private val windowScope: CoroutineScope,
) : WindowEventEmitter, WindowEventListener {
    private val _focusChanges = MutableSharedFlow<Boolean>(replay = 1)

    override val focusChanges: Flow<Boolean> =
            _focusChanges

    override fun onFocusChange(hasFocus: Boolean) {
        windowScope.launch {
            _focusChanges.emit(hasFocus)
        }
    }
}
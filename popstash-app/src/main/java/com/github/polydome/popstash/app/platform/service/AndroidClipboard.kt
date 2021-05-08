package com.github.polydome.popstash.app.platform.service

import android.content.ClipboardManager
import com.github.polydome.popstash.app.presentation.service.Clipboard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AndroidClipboard @Inject constructor(
        private val clipboardManager: ClipboardManager,
        private val windowEventEmitter: WindowEventEmitter,
) : Clipboard {
    private val clipboardChanges = MutableSharedFlow<Boolean>()

    init {
        clipboardManager.addPrimaryClipChangedListener {
            clipboardChanges.tryEmit(true)
        }
    }

    override fun getText(): String? {
        if (!clipboardManager.hasPrimaryClip()) {
            return null
        }

        return clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
    }

    @ExperimentalCoroutinesApi
    override fun contents(): Flow<String> =
            merge(windowEventEmitter.focusChanges, clipboardChanges)
                    .filter { hasFocus -> hasFocus }
                    .map { getText() }
                    .filterNotNull()
}
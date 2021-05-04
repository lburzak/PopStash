package com.github.polydome.popstash.app.platform.service

import android.content.ClipboardManager
import com.github.polydome.popstash.app.presentation.service.Clipboard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AndroidClipboard @Inject constructor(
        private val clipboardManager: ClipboardManager,
        private val windowEventEmitter: WindowEventEmitter,
) : Clipboard {
    override fun getText(): String? {
        if (!clipboardManager.hasPrimaryClip()) {
            return null
        }

        return clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
    }

    override fun contents(): Flow<String> =
            windowEventEmitter.focusChanges
                    .filter { hasFocus -> hasFocus }
                    .map { getText() }
                    .filterNotNull()
}
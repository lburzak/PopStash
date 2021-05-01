package com.github.polydome.popstash.app.service

import android.content.ClipboardManager
import com.github.polydome.popstash.app.viewmodel.Clipboard
import javax.inject.Inject

class AndroidClipboard @Inject constructor(private val clipboardManager: ClipboardManager) : Clipboard {
    override fun getText(): String =
            clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
}
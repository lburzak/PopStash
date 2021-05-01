package com.github.polydome.popstash.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.domain.usecase.SaveResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StashViewModel @Inject constructor(private val saveResource: SaveResource,
                     private val clipboard: Clipboard) : ViewModel() {
    init {
        saveUrlInClipboard()
    }

    private fun saveUrlInClipboard() {
        viewModelScope.launch {
            val url = clipboard.getText()
            withContext(Dispatchers.IO) {
                saveResource.execute(url)
            }
        }
    }
}
package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.domain.usecase.SaveResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveFromClipboardViewModel @Inject constructor(
        private val saveResource: SaveResource,
        private val clipboard: Clipboard,
) : ViewModel() {
    private val _urlInClipboard = MutableLiveData(clipboard.getText())

    val urlInClipboard = _urlInClipboard

    fun saveUrlFromClipboard() {
        viewModelScope.launch {
            val url = clipboard.getText()

            withContext(Dispatchers.IO) {
                saveResource.execute(url)
            }
        }
    }

    fun updateVisibleUrl() {
        _urlInClipboard.postValue(clipboard.getText())
    }
}
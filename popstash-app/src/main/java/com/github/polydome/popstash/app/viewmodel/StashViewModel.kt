package com.github.polydome.popstash.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.domain.usecase.SaveResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StashViewModel @Inject constructor(
        private val saveResource: SaveResource,
        private val clipboard: Clipboard,
) : ViewModel() {

    private val clipboardContents = flow {
        while (true) {
            emit(clipboard.getText())
            delay(5 * 1000)
        }
    }

    private val _isUrlInClipboard = MutableLiveData<Boolean>()
    val isUrlInClipboard: LiveData<Boolean> = _isUrlInClipboard

    fun saveUrlFromClipboard() {
        viewModelScope.launch {
            val url = clipboard.getText()
            withContext(Dispatchers.IO) {
                saveResource.execute(url)
            }
        }
    }

    fun monitorClipboard() {
        viewModelScope.launch {
            clipboardContents
                    .map { content -> android.util.Patterns.WEB_URL.matcher(content).matches() }
                    .collect {
                        _isUrlInClipboard.postValue(it)
                    }
        }
    }
}
package com.github.polydome.popstash.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.domain.usecase.SaveResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StashViewModel @Inject constructor(private val saveResource: SaveResource,
                     private val clipboard: Clipboard) : ViewModel() {

    init {
        viewModelScope.launch {
            clipboardContents.collect {
                _isUrlInClipboard.postValue(it)
            }
        }
    }

    private val clipboardContents = flow {
        while (true) {
            emit(clipboard.getText())
            delay(5 * 60 * 1000)
        }
    }.map { content -> android.util.Patterns.WEB_URL.matcher(content).matches() }

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
}
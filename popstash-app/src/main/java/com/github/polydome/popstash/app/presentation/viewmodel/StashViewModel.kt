package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import com.github.polydome.popstash.app.util.Millis
import com.github.polydome.popstash.app.util.intervalFlow
import com.github.polydome.popstash.domain.usecase.ListStashedUrls
import com.github.polydome.popstash.domain.usecase.SaveResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StashViewModel @Inject constructor(
        private val saveResource: SaveResource,
        private val listStashedUrls: ListStashedUrls,
        private val clipboard: Clipboard,
        private val patternMatcher: PatternMatcher,
) : ViewModel() {

    private val clipboardContents =
            intervalFlow(Millis.ofSeconds(5), clipboard::getText)
    private val _isUrlInClipboard = MutableLiveData<Boolean>()

    val isUrlInClipboard: LiveData<Boolean> = _isUrlInClipboard
    val urls: Flow<List<String>> = listStashedUrls.execute()

    fun saveUrlFromClipboard() {
        viewModelScope.launch {
            val url = clipboard.getText()

            withContext(Dispatchers.IO) {
                saveResource.execute(url)
            }
        }
    }

    fun checkClipboardForUrl() {
        val content = clipboard.getText()

        if (patternMatcher.matchUrl(content))
            _isUrlInClipboard.postValue(true)
    }

    fun monitorClipboard() {
        viewModelScope.launch {
            clipboardContents
                    .map(patternMatcher::matchUrl)
                    .collect(_isUrlInClipboard::postValue)
        }
    }
}
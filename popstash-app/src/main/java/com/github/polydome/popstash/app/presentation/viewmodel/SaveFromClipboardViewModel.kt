package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import com.github.polydome.popstash.domain.usecase.SaveResource
import com.github.polydome.popstash.domain.usecase.WatchResourceExists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveFromClipboardViewModel @Inject constructor(
        private val saveResource: SaveResource,
        private val watchResourceExists: WatchResourceExists,
        private val clipboard: Clipboard,
        private val patternMatcher: PatternMatcher
) : ViewModel() {
    private val isUrlInClipboard = clipboard.contents()
            .map(patternMatcher::matchUrl)

    private val urlsFromClipboard = clipboard.contents()
            .filter(patternMatcher::matchUrl)

    private val lastUrlExists = urlsFromClipboard
            .flatMapLatest(watchResourceExists::execute)

    val shouldDisplayDialog: LiveData<Boolean> =
            isUrlInClipboard
                    .combine(lastUrlExists) { isUrl, alreadyExists -> isUrl && !alreadyExists }
                    .asLiveData()

    val urlInClipboard: LiveData<String> =
            urlsFromClipboard
                    .asLiveData()

    fun saveUrlFromClipboard() {
        val url = clipboard.getText() ?: return

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                saveResource.execute(url)
            }
        }
    }
}
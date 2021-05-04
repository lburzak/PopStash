package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.*
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import com.github.polydome.popstash.domain.usecase.CheckResourceInStash
import com.github.polydome.popstash.domain.usecase.SaveResource
import com.github.polydome.popstash.domain.usecase.WatchResourceExists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveFromClipboardViewModel @Inject constructor(
        private val saveResource: SaveResource,
        private val checkResourceInStash: CheckResourceInStash,
        private val watchResourceExists: WatchResourceExists,
        private val clipboard: Clipboard,
        private val patternMatcher: PatternMatcher
) : ViewModel() {
    private val _shouldDisplayDialog = MutableLiveData<Boolean>()
    private val _urlInClipboard = MutableLiveData<String>()

    private val isUrlInClipboard = clipboard.contents()
            .map { patternMatcher.matchUrl(it) }

    private val urlsFromClipboard = clipboard.contents()
            .filter { patternMatcher.matchUrl(it) }

    private val lastUrlExists = urlsFromClipboard
            .flatMapLatest { url -> watchResourceExists.execute(url) }

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

            _shouldDisplayDialog.postValue(false)
        }
    }

    fun checkClipboardForUrl() {
        val clipboardContent = clipboard.getText() ?: return

        val isUrlInClipboard = patternMatcher.matchUrl(clipboardContent)

        viewModelScope.launch {
            val urlAlreadyExists = withContext(Dispatchers.IO) {
                checkResourceInStash.execute(clipboardContent)
            }

            _urlInClipboard.postValue(clipboardContent)
            _shouldDisplayDialog.postValue(isUrlInClipboard && !urlAlreadyExists)
        }
    }
}
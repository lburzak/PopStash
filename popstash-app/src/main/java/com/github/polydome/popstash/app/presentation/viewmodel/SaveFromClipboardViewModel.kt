package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.app.presentation.common.ReactiveViewModel
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import com.github.polydome.popstash.domain.usecase.SaveResource
import com.github.polydome.popstash.domain.usecase.WatchResourceExists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SaveFromClipboardViewModel @Inject constructor(
        saveResource: SaveResource,
        watchResourceExists: WatchResourceExists,
        clipboard: Clipboard,
        patternMatcher: PatternMatcher,
) : ReactiveViewModel<SaveFromClipboardViewModel.Command>() {
    enum class Command {
        SAVE
    }

    private val isUrlInClipboard = clipboard.contents()
            .map(patternMatcher::matchUrl)

    private val url = clipboard.contents()
            .filter(patternMatcher::matchUrl)

    private val urlAlreadySaved = url
            .flatMapLatest(watchResourceExists::execute)

    private val urlCache = FlowCache.of(url)
            .cacheIn(viewModelScope)

    val shouldDisplayDialog: LiveData<Boolean> =
            isUrlInClipboard
                    .combine(urlAlreadySaved) { isUrl, alreadySaved -> isUrl && !alreadySaved }
                    .asLiveData()

    val urlInClipboard: LiveData<String> = url.asLiveData()

    init {
        commands.filter { command -> command == Command.SAVE }
                .map { urlCache.last }
                .map(saveResource::execute)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
    }
}
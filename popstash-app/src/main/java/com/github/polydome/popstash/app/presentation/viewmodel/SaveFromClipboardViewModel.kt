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

    private val urlsFromClipboard = clipboard.contents()
            .filter(patternMatcher::matchUrl)

    private val lastUrlExists = urlsFromClipboard
            .flatMapLatest(watchResourceExists::execute)

    val shouldDisplayDialog: LiveData<Boolean> =
            isUrlInClipboard
                    .combine(lastUrlExists) { isUrl, alreadyExists ->
                        isUrl && !alreadyExists
                    }
                    .asLiveData()

    val urlInClipboard: LiveData<String> =
            urlsFromClipboard
                    .asLiveData()

    init {
        commands.filter { command -> command == Command.SAVE }
                .zip(urlsFromClipboard) { _, url -> url }
                .map(saveResource::execute)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
    }
}
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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveFromClipboardViewModel @Inject constructor(
        private val saveResource: SaveResource,
        private val watchResourceExists: WatchResourceExists,
        private val clipboard: Clipboard,
        private val patternMatcher: PatternMatcher
) : ViewModel() {
    private val commands = MutableSharedFlow<Command>()

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

    init {
        commands.filter { command -> command == Command.SAVE }
                .flatMapLatest { urlsFromClipboard }
                .map(saveResource::execute)
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
    }

    fun saveUrlFromClipboard() {
        viewModelScope.launch { commands.emit(Command.SAVE) }
    }

    enum class Command {
        SAVE
    }
}
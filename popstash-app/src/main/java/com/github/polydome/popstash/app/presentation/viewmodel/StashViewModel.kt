package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.service.PatternMatcher
import com.github.polydome.popstash.domain.usecase.ListStashedUrls
import com.github.polydome.popstash.domain.usecase.SaveResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StashViewModel @Inject constructor(
        private val clipboard: Clipboard,
        private val patternMatcher: PatternMatcher,
        listStashedUrls: ListStashedUrls,
) : ViewModel() {
    private val _isUrlInClipboard = MutableLiveData<Boolean>()

    val isUrlInClipboard: LiveData<Boolean> = _isUrlInClipboard
    val urls: Flow<List<String>> = listStashedUrls.execute()

    fun checkClipboardForUrl() {
        _isUrlInClipboard.postValue(clipboardContainsUrl())
    }

    private fun clipboardContainsUrl(): Boolean {
        val clipboardContent = clipboard.getText()
        return patternMatcher.matchUrl(clipboardContent)
    }
}
package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.domain.usecase.ListStashedUrls
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StashViewModel @Inject constructor(listStashedUrls: ListStashedUrls) : ViewModel() {
    val urls: Flow<List<String>> = listStashedUrls.execute()
    val stashEmpty = urls
            .map { urls -> urls.count() }
            .map { urlsCount -> urlsCount == 0 }
            .asLiveData(viewModelScope.coroutineContext)
}
package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.github.polydome.popstash.domain.usecase.ListStashedUrls
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StashViewModel @Inject constructor(listStashedUrls: ListStashedUrls) : ViewModel() {
    val urls: Flow<List<String>> = listStashedUrls.execute()
}
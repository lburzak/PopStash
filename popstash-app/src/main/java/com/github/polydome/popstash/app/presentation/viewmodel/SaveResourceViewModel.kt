package com.github.polydome.popstash.app.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveResourceViewModel @Inject constructor() {
    val url = MutableStateFlow("")
    val outcome = url.map { Outcome.FAILURE }

    enum class Outcome {
        SUCCESS,
        FAILURE
    }
}
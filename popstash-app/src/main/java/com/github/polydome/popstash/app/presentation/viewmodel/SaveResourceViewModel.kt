package com.github.polydome.popstash.app.presentation.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveResourceViewModel @Inject constructor() {
    val url = MutableSharedFlow<String>()
    val outcome = url
            .map { Outcome.SUCCESS }

    enum class Outcome {
        SUCCESS,
        FAILURE
    }
}
package com.github.polydome.popstash.app.presentation.viewmodel

import com.github.polydome.popstash.domain.usecase.SaveResource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveResourceViewModel @Inject constructor(private val saveResource: SaveResource) {
    val url = MutableSharedFlow<String>()
    val outcome = url
            .map { url -> saveResource.execute(url) }
            .map { result -> when (result) {
                is SaveResource.Result.Success -> Outcome.SUCCESS
                is SaveResource.Result.Failure -> Outcome.FAILURE
            } }

    enum class Outcome {
        SUCCESS,
        FAILURE
    }
}
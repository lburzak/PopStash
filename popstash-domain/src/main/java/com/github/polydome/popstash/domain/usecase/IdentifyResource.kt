package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.github.polydome.popstash.domain.service.ParserService
import javax.inject.Inject

class IdentifyResource @Inject constructor(private val parserService: ParserService) {
    fun execute(url: String): Result {
        val resource = parserService.parse(url)

        if (resource != null)
            return Result.Success(resource.metadata)

        return Result.Failure
    }

    sealed class Result {
        data class Success(val metadata: ResourceMetadata): Result()
        object Failure: Result()
    }
}
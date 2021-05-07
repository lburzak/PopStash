package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.repository.ResourceRepository
import com.github.polydome.popstash.domain.service.URLValidator
import javax.inject.Inject

class SaveResource @Inject constructor(
        private val resourceRepository: ResourceRepository,
        private val urlValidator: URLValidator,
) {
    suspend fun execute(url: String): Result {
        val isUrlValid = urlValidator.validateUrl(url)

        if (!isUrlValid)
            return Result.Failure(Error.URL_INVALID)

        val resourceExists = resourceRepository.existsResourceByUrl(url)

        if (resourceExists)
            return Result.Failure(Error.RESOURCE_ALREADY_EXISTS)

        val resource = Resource(url = url)

        resourceRepository.insertOne(resource)
        return Result.Success
    }

    sealed class Result {
        object Success: Result()
        data class Failure(val error: Error): Result()
    }

    enum class Error {
        RESOURCE_ALREADY_EXISTS,
        URL_INVALID
    }
}
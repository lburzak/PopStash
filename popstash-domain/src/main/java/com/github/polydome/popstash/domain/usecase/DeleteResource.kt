package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.repository.ResourceRepository
import javax.inject.Inject

class DeleteResource @Inject constructor(private val resourceRepository: ResourceRepository) {
    suspend fun execute(url: String): Result {
        val resourceExists = resourceRepository.existsResourceByUrl(url)

        if (resourceExists) {
            resourceRepository.removeOne(url)
            return Result.Success
        }
        return Result.Failure(Error.NO_SUCH_RESOURCE)
    }

    sealed class Result {
        object Success: Result()
        data class Failure(val error: Error): Result()
    }

    enum class Error {
        NO_SUCH_RESOURCE
    }
}
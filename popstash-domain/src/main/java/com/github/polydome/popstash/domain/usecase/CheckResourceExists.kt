package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.repository.ResourceRepository
import javax.inject.Inject

class CheckResourceExists @Inject constructor(private val resourceRepository: ResourceRepository) {
    suspend fun execute(url: String): Boolean =
            resourceRepository.existsResourceByUrl(url)
}
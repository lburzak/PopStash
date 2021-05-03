package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.repository.ResourceRepository

class CheckResourceExists(private val resourceRepository: ResourceRepository) {
    suspend fun execute(url: String): Boolean =
            resourceRepository.existsResourceByUrl(url)
}
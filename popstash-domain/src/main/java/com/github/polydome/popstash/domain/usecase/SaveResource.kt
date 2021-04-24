package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.repository.ResourceRepository

class SaveResource(private val resourceRepository: ResourceRepository) {
    suspend fun execute(url: String) {
        val resource = Resource(url = url)

        resourceRepository.insert(resource)
    }
}
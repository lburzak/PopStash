package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow

class ListStashedUrls(private val resourceRepository: ResourceRepository) {
    fun execute(): Flow<List<String>> {
        return resourceRepository.watchAllUrls()
    }
}
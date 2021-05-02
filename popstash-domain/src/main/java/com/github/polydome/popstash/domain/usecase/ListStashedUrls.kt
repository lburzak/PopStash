package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListStashedUrls @Inject constructor(private val resourceRepository: ResourceRepository) {
    fun execute(): Flow<List<String>> {
        return resourceRepository.watchAllUrls()
    }
}
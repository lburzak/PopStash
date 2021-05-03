package com.github.polydome.popstash.domain.usecase

import com.github.polydome.popstash.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchResourceExists @Inject constructor(private val resourceRepository: ResourceRepository) {
    fun execute(url: String): Flow<Boolean> =
            resourceRepository.watchUrlExists(url)
}
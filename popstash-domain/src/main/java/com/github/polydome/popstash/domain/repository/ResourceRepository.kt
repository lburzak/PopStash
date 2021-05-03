package com.github.polydome.popstash.domain.repository

import com.github.polydome.popstash.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ResourceRepository {
    suspend fun insertOne(resource: Resource)
    fun watchAllUrls(): Flow<List<String>>
    fun watchUrlExists(url: String): Flow<Boolean>
    suspend fun existsResourceByUrl(url: String): Boolean
}

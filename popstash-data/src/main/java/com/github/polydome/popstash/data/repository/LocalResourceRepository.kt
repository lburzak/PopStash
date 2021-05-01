package com.github.polydome.popstash.data.repository

import com.github.polydome.popstash.data.dao.ResourceDao
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalResourceRepository @Inject constructor(private val resourceDao: ResourceDao) : ResourceRepository {
    override suspend fun insertOne(resource: Resource) {
        resourceDao.insertOne(resource.toEntity())
    }

    override fun watchAllUrls(): Flow<List<String>> =
            resourceDao.getAllUrls()
}
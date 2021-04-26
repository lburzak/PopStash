package com.github.polydome.popstash.data.repository

import com.github.polydome.popstash.data.dao.ResourceDao
import com.github.polydome.popstash.data.entity.ResourceEntity
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.repository.ResourceRepository

class LocalResourceRepository(private val resourceDao: ResourceDao) : ResourceRepository {
    override suspend fun insertOne(resource: Resource) {
        resourceDao.insertOne(resource.toEntity())
    }
}
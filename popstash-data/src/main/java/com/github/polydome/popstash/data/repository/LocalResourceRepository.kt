package com.github.polydome.popstash.data.repository

import com.github.polydome.popstash.data.dao.ResourceDao
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.domain.exception.NoSuchResourceException
import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalResourceRepository @Inject constructor(private val resourceDao: ResourceDao) : ResourceRepository {
    override suspend fun insertOne(resource: Resource) {
        resourceDao.insertOne(resource.toEntity())
    }

    override fun watchAllUrls(): Flow<List<String>> =
            resourceDao.getAllUrls()

    override fun watchUrlExists(url: String): Flow<Boolean> =
            resourceDao.checkUrlExists(url)

    override suspend fun existsResourceByUrl(url: String): Boolean =
            resourceDao.existsResourceByUrl(url)

    override suspend fun removeOne(url: String) {
        val entity = resourceDao.findOneByUrl(url) ?: throw NoSuchResourceException()

        resourceDao.removeOne(entity)
    }
}
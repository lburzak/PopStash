package com.github.polydome.popstash.data.repository

import com.github.polydome.popstash.data.dao.ResourceDao
import com.github.polydome.popstash.data.dao.ResourceMetadataDao
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.data.entity.toModel
import com.github.polydome.popstash.domain.exception.NoSuchResourceException
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.github.polydome.popstash.domain.service.MetadataCache
import javax.inject.Inject

class DatabaseMetadataCache @Inject constructor(
        private val resourceMetadataDao: ResourceMetadataDao,
        private val resourceDao: ResourceDao,
) : MetadataCache {
    override suspend fun put(url: String, metadata: ResourceMetadata) {
        val resourceExists = resourceDao.existsResourceByUrl(url)

        if (!resourceExists)
            throw NoSuchResourceException()

        val metadataExists = resourceMetadataDao.existsByUrl(url)
        val newEntity = metadata.toEntity(url)

        if (metadataExists) {
            resourceMetadataDao.updateOne(newEntity)
        } else  {
            resourceMetadataDao.insertOne(newEntity)
        }
    }

    override suspend fun get(url: String): ResourceMetadata? {
        return resourceMetadataDao.findOneByUrl(url)?.toModel()
    }
}
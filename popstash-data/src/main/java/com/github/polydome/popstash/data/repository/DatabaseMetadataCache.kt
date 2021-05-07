package com.github.polydome.popstash.data.repository

import com.github.polydome.popstash.data.dao.ResourceMetadataDao
import com.github.polydome.popstash.data.entity.toEntity
import com.github.polydome.popstash.data.entity.toModel
import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.github.polydome.popstash.domain.service.MetadataCache

class DatabaseMetadataCache(private val resourceMetadataDao: ResourceMetadataDao) : MetadataCache {
    override suspend fun put(url: String, metadata: ResourceMetadata) {
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
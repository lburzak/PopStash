package com.github.polydome.popstash.domain.service

import com.github.polydome.popstash.domain.model.ResourceMetadata
import com.github.polydome.popstash.domain.exception.NoSuchResourceException

interface MetadataCache {
    /**
     * @throws NoSuchResourceException when attempting to cache metadata of non-existing resource
     */
    suspend fun put(url: String, metadata: ResourceMetadata)
    suspend fun get(url: String): ResourceMetadata?
}
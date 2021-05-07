package com.github.polydome.popstash.domain.service

import com.github.polydome.popstash.domain.model.ResourceMetadata

interface MetadataCache {
    suspend fun put(url: String, metadata: ResourceMetadata)
    suspend fun get(url: String): ResourceMetadata?
}
package com.github.polydome.popstash.domain.repository

import com.github.polydome.popstash.domain.model.Resource

interface ResourceRepository {
    suspend fun insertOne(resource: Resource)
}

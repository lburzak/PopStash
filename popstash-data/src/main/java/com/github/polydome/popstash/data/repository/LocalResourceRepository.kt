package com.github.polydome.popstash.data.repository

import com.github.polydome.popstash.domain.model.Resource
import com.github.polydome.popstash.domain.repository.ResourceRepository

class LocalResourceRepository : ResourceRepository {
    override suspend fun insert(resource: Resource) {
        TODO("Not yet implemented")
    }
}
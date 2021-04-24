package com.github.polydome.popstash.domain.repository

import com.github.polydome.popstash.domain.model.Resource

interface ResourceRepository {
    fun insert(resource: Resource)
}

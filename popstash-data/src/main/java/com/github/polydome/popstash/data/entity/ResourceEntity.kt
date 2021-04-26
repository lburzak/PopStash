package com.github.polydome.popstash.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.polydome.popstash.domain.model.Resource

@Entity(tableName = "resource")
data class ResourceEntity(
        @PrimaryKey
        val url: String
)

fun Resource.toEntity(): ResourceEntity = ResourceEntity(
        url = url
)
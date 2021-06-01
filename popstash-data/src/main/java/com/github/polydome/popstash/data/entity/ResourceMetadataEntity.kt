package com.github.polydome.popstash.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.github.polydome.popstash.domain.model.ResourceMetadata

@Entity(
        tableName = "resource_metadata",
        foreignKeys = [
                ForeignKey(
                        entity = ResourceEntity::class,
                        parentColumns = ["url"],
                        childColumns = ["url"],
                        onDelete = ForeignKey.CASCADE
                )
        ]
)
data class ResourceMetadataEntity(
        @PrimaryKey
        val url: String,
        val title: String,
        val summary: String,
        val site: String,
        val author: String?
)

fun ResourceMetadata.toEntity(url: String): ResourceMetadataEntity =
        ResourceMetadataEntity(
                url = url,
                title = title,
                summary = summary,
                site = site,
                author = author
        )

fun ResourceMetadataEntity.toModel(): ResourceMetadata =
        ResourceMetadata(
                title = title,
                summary = summary,
                site = site,
                author = author,
                thumbnailUrl = null
        )

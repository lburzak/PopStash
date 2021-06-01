package com.github.polydome.popstash.domain.model

data class ResourceMetadata(
        val title: String,
        val summary: String,
        val site: String,
        val author: String?,
        val thumbnailUrl: String?,
)
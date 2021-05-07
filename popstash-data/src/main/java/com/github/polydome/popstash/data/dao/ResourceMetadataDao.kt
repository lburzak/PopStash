package com.github.polydome.popstash.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.polydome.popstash.data.entity.ResourceMetadataEntity

@Dao
interface ResourceMetadataDao {
    @Insert
    suspend fun insertOne(resourceMetadataEntity: ResourceMetadataEntity)

    @Query("select * from resource_metadata where url = :url")
    suspend fun findOneByUrl(url: String): ResourceMetadataEntity?

    @Query("select exists (select * from resource_metadata where url = :url)")
    suspend fun existsByUrl(url: String): Boolean

    @Update
    suspend fun updateOne(resourceMetadataEntity: ResourceMetadataEntity)
}
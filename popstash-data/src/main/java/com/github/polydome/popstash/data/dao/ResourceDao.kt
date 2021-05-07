package com.github.polydome.popstash.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.polydome.popstash.data.entity.ResourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourceDao {
    @Insert
    suspend fun insertOne(resource: ResourceEntity)

    @Query("select * from resource where resource.url = :url")
    fun findOneByUrl(url: String): ResourceEntity?

    @Query("select url from resource")
    fun getAllUrls(): Flow<List<String>>

    @Query("select exists (select * from resource where resource.url = :url)")
    fun watchExistsResourceByUrl(url: String): Flow<Boolean>

    @Query("select exists (select * from resource where resource.url = :url)")
    suspend fun existsResourceByUrl(url: String): Boolean

    @Delete
    fun removeOne(resourceEntity: ResourceEntity)
}
package com.github.polydome.popstash.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.polydome.popstash.data.entity.ResourceEntity

@Dao
interface ResourceDao {
    @Insert
    fun insertOne(resource: ResourceEntity)

    @Query("select * from resource where resource.url = :url")
    fun findOneByUrl(url: String): ResourceEntity
}
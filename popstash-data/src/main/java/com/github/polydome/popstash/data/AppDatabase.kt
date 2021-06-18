package com.github.polydome.popstash.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.polydome.popstash.data.dao.ResourceDao
import com.github.polydome.popstash.data.dao.ResourceMetadataDao
import com.github.polydome.popstash.data.entity.ResourceEntity
import com.github.polydome.popstash.data.entity.ResourceMetadataEntity

@Database(entities = [ResourceEntity::class, ResourceMetadataEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resourceDao(): ResourceDao
    abstract fun resourceMetadataDao(): ResourceMetadataDao
}
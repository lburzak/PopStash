package com.github.polydome.popstash.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.polydome.popstash.data.dao.ResourceDao
import com.github.polydome.popstash.data.entity.ResourceEntity

@Database(entities = [ResourceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resourceDao(): ResourceDao
}
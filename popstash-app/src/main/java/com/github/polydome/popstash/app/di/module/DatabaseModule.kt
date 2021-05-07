package com.github.polydome.popstash.app.di.module

import android.content.Context
import androidx.room.Room
import com.github.polydome.popstash.data.AppDatabase
import com.github.polydome.popstash.data.dao.ResourceDao
import com.github.polydome.popstash.data.dao.ResourceMetadataDao
import com.github.polydome.popstash.data.repository.LocalResourceRepository
import com.github.polydome.popstash.domain.repository.ResourceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun resourceDao(appDatabase: AppDatabase): ResourceDao =
            appDatabase.resourceDao()

    @Provides
    fun resourceMetadataDao(appDatabase: AppDatabase): ResourceMetadataDao =
            appDatabase.resourceMetadataDao()

    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "popstash-db")
                    .build()
}
package com.github.polydome.popstash.app.di.module

import com.github.polydome.popstash.data.repository.DatabaseMetadataCache
import com.github.polydome.popstash.domain.service.MetadataCache
import com.github.polydome.popstash.domain.service.ResourceParser
import com.github.polydome.popstash.parser.readability.ReadabilityParser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun resourceParser(readabilityParser: ReadabilityParser): ResourceParser

    @Binds
    abstract fun metadataCache(databaseMetadataCache: DatabaseMetadataCache): MetadataCache
}
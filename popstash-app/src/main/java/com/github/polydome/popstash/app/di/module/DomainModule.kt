package com.github.polydome.popstash.app.di.module

import com.github.polydome.popstash.app.platform.service.AndroidPatternMatcher
import com.github.polydome.popstash.data.repository.DatabaseMetadataCache
import com.github.polydome.popstash.data.repository.LocalResourceRepository
import com.github.polydome.popstash.domain.repository.ResourceRepository
import com.github.polydome.popstash.domain.service.MetadataCache
import com.github.polydome.popstash.domain.service.ResourceParser
import com.github.polydome.popstash.domain.service.URLValidator
import com.github.polydome.popstash.parser.readability.ReadabilityParser
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun resourceRepository(localResourceRepository: LocalResourceRepository): ResourceRepository

    @Binds
    abstract fun resourceParser(readabilityParser: ReadabilityParser): ResourceParser

    @Binds
    abstract fun metadataCache(databaseMetadataCache: DatabaseMetadataCache): MetadataCache

    @Binds
    abstract fun urlValidator(androidPatternMatcher: AndroidPatternMatcher): URLValidator
}
package com.github.polydome.popstash.app.di.module

import com.github.polydome.popstash.network.JavaDomainExtractor
import com.github.polydome.popstash.network.JavaHttpServer
import com.github.polydome.popstash.parser.service.DomainExtractor
import com.github.polydome.popstash.parser.service.HttpServer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun domainExtractor(javaDomainExtractor: JavaDomainExtractor): DomainExtractor

    @Singleton
    @Binds
    abstract fun httpServer(javaHttpServer: JavaHttpServer): HttpServer
}
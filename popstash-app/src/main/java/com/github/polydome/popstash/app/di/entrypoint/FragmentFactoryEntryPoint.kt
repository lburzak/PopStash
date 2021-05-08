package com.github.polydome.popstash.app.di.entrypoint

import com.github.polydome.popstash.app.platform.GenericFragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface FragmentFactoryEntryPoint {
    fun fragmentFactory(): GenericFragmentFactory
}
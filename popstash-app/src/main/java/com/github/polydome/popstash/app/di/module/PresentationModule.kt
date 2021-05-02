package com.github.polydome.popstash.app.di.module

import androidx.lifecycle.ViewModelProvider
import com.github.polydome.popstash.app.di.scope.BoundViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.StashViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object PresentationModule {
    @Provides
    @BoundViewModel
    fun stashViewModel(viewModelProvider: ViewModelProvider): StashViewModel =
            viewModelProvider.get(StashViewModel::class.java)
}
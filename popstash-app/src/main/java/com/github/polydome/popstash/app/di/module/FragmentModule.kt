package com.github.polydome.popstash.app.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.polydome.popstash.app.platform.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {
    @Provides
    fun viewModelProvider(
            fragment: Fragment,
            viewModelFactory: ViewModelFactory
    ): ViewModelProvider =
            ViewModelProvider(fragment, viewModelFactory)
}
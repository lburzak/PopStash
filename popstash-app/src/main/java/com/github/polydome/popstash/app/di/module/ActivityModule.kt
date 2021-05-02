package com.github.polydome.popstash.app.di.module

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.polydome.popstash.app.service.AndroidClipboard
import com.github.polydome.popstash.app.service.AndroidPatternMatcher
import com.github.polydome.popstash.app.viewmodel.Clipboard
import com.github.polydome.popstash.app.viewmodel.PatternMatcher
import com.github.polydome.popstash.app.viewmodel.StashViewModel
import com.github.polydome.popstash.app.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun lifecycleOwner(activity: FragmentActivity): LifecycleOwner {
        return activity
    }

    @Provides
    fun layoutInflater(@ActivityContext context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }

    @Provides
    fun linearLayoutManager(@ActivityContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    fun viewModelProvider(
            activity: FragmentActivity,
            viewModelFactory: ViewModelFactory,
    ): ViewModelProvider =
            ViewModelProvider(activity, viewModelFactory)
}
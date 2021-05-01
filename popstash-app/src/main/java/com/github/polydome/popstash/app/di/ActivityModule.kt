package com.github.polydome.popstash.app.di

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun lifecycleOwner(activity: FragmentActivity): LifecycleOwner {
        return activity
    }

    @Provides
    fun layoutInflater(@ActivityContext context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }

    companion object {
        @Provides
        fun linearLayoutManager(@ActivityContext context: Context): LinearLayoutManager {
            return LinearLayoutManager(context)
        }
    }
}
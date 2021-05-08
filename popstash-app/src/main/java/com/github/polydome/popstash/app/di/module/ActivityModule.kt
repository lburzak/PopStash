package com.github.polydome.popstash.app.di.module

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.polydome.popstash.app.platform.view.ItemSwipeCallback
import com.github.polydome.popstash.app.platform.ViewModelFactory
import com.github.polydome.popstash.app.platform.service.WindowEventBus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun lifecycleOwner(activity: FragmentActivity): LifecycleOwner =
            activity

    @Provides
    fun layoutInflater(@ActivityContext context: Context): LayoutInflater =
            LayoutInflater.from(context)

    @Provides
    fun linearLayoutManager(@ActivityContext context: Context): LinearLayoutManager =
            LinearLayoutManager(context)

    @Provides
    fun resourceItemTouchHelper(itemSwipeCallback: ItemSwipeCallback): ItemTouchHelper =
            ItemTouchHelper(itemSwipeCallback)

    @Provides
    fun viewModelProvider(
            activity: FragmentActivity,
            viewModelFactory: ViewModelFactory,
    ): ViewModelProvider =
            ViewModelProvider(activity, viewModelFactory)

    @Provides
    @ActivityScoped
    fun windowEventBus(windowScope: CoroutineScope): WindowEventBus = WindowEventBus(windowScope)

    @Provides
    @ActivityScoped
    fun windowScope(): CoroutineScope =
            CoroutineScope(Dispatchers.Main)
}
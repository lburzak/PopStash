package com.github.polydome.popstash.app.di.module

import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SystemModule {
    @Provides
    fun clipboardManager(@ApplicationContext context: Context): ClipboardManager =
            ContextCompat.getSystemService(context, ClipboardManager::class.java) as ClipboardManager
}
package com.github.polydome.popstash.data.test

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.github.polydome.popstash.data.AppDatabase

private fun getInstrumentationContext(): Context =
    InstrumentationRegistry.getInstrumentation().targetContext

fun createInMemoryDatabase(): AppDatabase
    = Room.inMemoryDatabaseBuilder(getInstrumentationContext(), AppDatabase::class.java).build()
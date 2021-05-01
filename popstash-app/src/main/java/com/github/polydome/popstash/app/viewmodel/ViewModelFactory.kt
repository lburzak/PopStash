package com.github.polydome.popstash.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(private val stashViewModelProvider: Provider<StashViewModel>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        StashViewModel::class.java -> stashViewModelProvider.get() as T
        else ->
            throw IllegalArgumentException(
                    "Unable to create ViewModel for class [%s].".format(modelClass)
            )
    }
}
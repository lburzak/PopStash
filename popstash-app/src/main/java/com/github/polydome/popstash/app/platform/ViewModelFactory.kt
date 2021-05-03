package com.github.polydome.popstash.app.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.polydome.popstash.app.presentation.viewmodel.SaveFromClipboardViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.StashViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
        private val stashViewModelProvider: Provider<StashViewModel>,
        private val saveFromClipboardViewModelProvider: Provider<SaveFromClipboardViewModel>,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        StashViewModel::class.java -> stashViewModelProvider.get() as T
        SaveFromClipboardViewModel::class.java -> saveFromClipboardViewModelProvider.get() as T
        else ->
            throw IllegalArgumentException(
                    "Unable to create ViewModel for class [%s].".format(modelClass)
            )
    }
}
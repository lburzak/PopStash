package com.github.polydome.popstash.app.platform

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.polydome.popstash.app.feature.stash.SaveFromClipboardFragment
import com.github.polydome.popstash.app.feature.stash.StashFragment
import javax.inject.Inject
import javax.inject.Provider

class GenericFragmentFactory @Inject constructor(
        private val saveFromClipboardFragmentProvider: Provider<SaveFromClipboardFragment>,
        private val stashFragmentProvider: Provider<StashFragment>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SaveFromClipboardFragment::class.java.name -> saveFromClipboardFragmentProvider.get()
            StashFragment::class.java.name -> stashFragmentProvider.get()
            else -> super.instantiate(classLoader, className)
        }
    }
}
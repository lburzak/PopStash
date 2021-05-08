package com.github.polydome.popstash.app.feature.stash

import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.di.qualifier.BoundViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.StashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class StashAdapter @Inject constructor(private val viewHolderFactory: ResourceViewHolder.Factory,
                                       @BoundViewModel private val stashViewModel: StashViewModel
) : RecyclerView.Adapter<ResourceViewHolder>() {
    private var urls = listOf<String>()
    private val uiScope: CoroutineScope =
            CoroutineScope(Dispatchers.Main)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder =
            viewHolderFactory.create(parent)

    override fun getItemCount() =
            urls.count()

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.onChangeUrl(urls[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        uiScope.launch {
            observeUrlList()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        uiScope.cancel()
    }

    private suspend fun observeUrlList() {
        stashViewModel.urls
                .flowOn(Dispatchers.IO)
                .collect(::updateUrlList)
    }

    @MainThread
    private fun updateUrlList(urls: List<String>) {
        this.urls = urls
        notifyDataSetChanged()
    }
}
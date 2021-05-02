package com.github.polydome.popstash.app.feature.stash

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.di.scope.BoundViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.StashViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class StashAdapter @Inject constructor(private val viewHolderFactory: ResourceViewHolder.Factory,
                                       @BoundViewModel private val stashViewModel: StashViewModel
) : RecyclerView.Adapter<ResourceViewHolder>() {
    private var items = listOf<String>()
    private val coroutineScope: CoroutineScope =
            CoroutineScope(Dispatchers.IO)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder =
            viewHolderFactory.create(parent)

    override fun getItemCount() =
            items.count()


    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.showUrl(items[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        coroutineScope.launch {
            stashViewModel.urls
                    .collect { urls ->
                        items = urls
                        withContext(Dispatchers.Main) {
                            notifyDataSetChanged()
                        }
                    }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        coroutineScope.cancel()
    }
}
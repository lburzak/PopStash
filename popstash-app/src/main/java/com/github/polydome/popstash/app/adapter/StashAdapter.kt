package com.github.polydome.popstash.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.model.StashListModel
import com.github.polydome.popstash.app.viewholder.ResourceViewHolder
import javax.inject.Inject

class StashAdapter @Inject constructor(private val viewHolderFactory: ResourceViewHolder.Factory,
                                       private val model: StashListModel) : RecyclerView.Adapter<ResourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder =
            viewHolderFactory.create(parent)

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.showUrl(model.itemAt(position))
    }

    override fun getItemCount() = model.countItems()
}
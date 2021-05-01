package com.github.polydome.popstash.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.viewholder.ResourceViewHolder
import javax.inject.Inject

class StashAdapter @Inject constructor(private val viewHolderFactory: ResourceViewHolder.Factory) : RecyclerView.Adapter<ResourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder =
            viewHolderFactory.create(parent)

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.showUrl("http://example.com")
    }

    override fun getItemCount(): Int {
        return 3
    }
}
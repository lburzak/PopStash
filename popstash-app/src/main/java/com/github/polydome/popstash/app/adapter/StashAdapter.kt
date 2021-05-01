package com.github.polydome.popstash.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.databinding.RowResourceBinding
import com.github.polydome.popstash.app.viewholder.ResourceViewHolder
import com.github.polydome.popstash.app.viewmodel.ResourceViewModel

class StashAdapter(private val layoutInflater: LayoutInflater, private val recyclerViewOwner: LifecycleOwner) : RecyclerView.Adapter<ResourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val binding = RowResourceBinding.inflate(layoutInflater, parent, false)
        val viewModel = ResourceViewModel()

        return with(binding) {
            lifecycleOwner = recyclerViewOwner
            viewmodel = viewModel
            ResourceViewHolder(root, viewModel)
        }
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.showUrl("http://example.com")
    }

    override fun getItemCount(): Int {
        return 3
    }
}
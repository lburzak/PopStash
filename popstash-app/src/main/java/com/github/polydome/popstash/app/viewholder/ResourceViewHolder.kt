package com.github.polydome.popstash.app.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.viewmodel.ResourceViewModel

class ResourceViewHolder(itemView: View, private val viewModel: ResourceViewModel) : RecyclerView.ViewHolder(itemView) {
    fun showUrl(url: String) {
        viewModel.showUrl(url)
    }
}
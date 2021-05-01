package com.github.polydome.popstash.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.viewholder.ResourceViewHolder

class StashAdapter(private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<ResourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val view =  layoutInflater.inflate(R.layout.row_resource, parent, false)
        return ResourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }
}
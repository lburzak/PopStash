package com.github.polydome.popstash.app.feature.stash

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDelete : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        require(viewHolder is ResourceViewHolder)

        viewHolder.delete()
    }

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
    ): Boolean = false

}
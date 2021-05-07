package com.github.polydome.popstash.app.feature.stash

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class SwipeToDelete @Inject constructor() : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        require(viewHolder is ResourceViewHolder)

        viewHolder.onSwipe()
    }

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
    ): Boolean = false

}
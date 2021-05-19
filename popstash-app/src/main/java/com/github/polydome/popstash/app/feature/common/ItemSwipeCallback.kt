package com.github.polydome.popstash.app.feature.common

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class ItemSwipeCallback @Inject constructor() : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        require(viewHolder is Swipeable)

        viewHolder.onSwiped()
    }

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
    ): Boolean = false

}
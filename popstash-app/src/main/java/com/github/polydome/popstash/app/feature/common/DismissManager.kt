package com.github.polydome.popstash.app.feature.common

import android.annotation.SuppressLint
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.behavior.SwipeDismissBehavior

class DismissManager <V: View> private constructor(private val view: V) {
    companion object {
        fun <V: View> of(view: V): DismissManager<V> =
                DismissManager(view)
    }

    init {
        require(view.parent is CoordinatorLayout)
    }

    private val parent: CoordinatorLayout
        get() = view.parent as CoordinatorLayout

    private var layoutParams: CoordinatorLayout.LayoutParams
        get() = view.layoutParams as CoordinatorLayout.LayoutParams
        set(value) { view.layoutParams = value }

    var isDismissed: Boolean = false
        private set

    private var savedParams: CoordinatorLayout.LayoutParams = layoutParams

    fun enableSwipeToDismiss() {
        saveLayoutParams()
        
        val dismissBehavior = createDismissBehavior()
        applyTouchBehavior(dismissBehavior)
    }

    fun restore() {
        restoreLayoutParams()
        restoreVisibility()
        isDismissed = false
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun applyTouchBehavior(behavior: CoordinatorLayout.Behavior<V>) {
        layoutParams.behavior = behavior

        view.setOnTouchListener { _, event ->
            behavior.onTouchEvent(parent, view, event)
        }
    }

    private fun createDismissBehavior() =
            SwipeDismissBehavior<V>().apply {
                setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
                listener = DismissListener()
            }

    private fun saveLayoutParams() {
        savedParams = layoutParams
    }

    private fun restoreLayoutParams() {
        layoutParams = savedParams
    }
    
    private fun restoreVisibility() {
        view.alpha = 1.0f
    }

    private inner class DismissListener: SwipeDismissBehavior.OnDismissListener {
        override fun onDismiss(view: View?) {
            isDismissed = true
        }

        override fun onDragStateChanged(state: Int) {}
    }
}
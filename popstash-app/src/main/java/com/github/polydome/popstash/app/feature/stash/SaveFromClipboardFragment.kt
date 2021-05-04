package com.github.polydome.popstash.app.feature.stash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.github.polydome.popstash.app.databinding.FragmentSaveFromClipboardBinding
import com.github.polydome.popstash.app.di.qualifier.BoundViewModel
import com.github.polydome.popstash.app.presentation.viewmodel.SaveFromClipboardViewModel
import com.google.android.material.behavior.SwipeDismissBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SaveFromClipboardFragment : Fragment() {
    @Inject
    @BoundViewModel
    lateinit var viewModel: SaveFromClipboardViewModel

    private lateinit var binding: FragmentSaveFromClipboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveFromClipboardContent.applySwipeToDismiss()
    }

    private fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSaveFromClipboardBinding {
        return FragmentSaveFromClipboardBinding.inflate(inflater, container, false)
                .also {
                    it.lifecycleOwner = this
                    it.viewModel = viewModel
                }
    }

    private fun <T: View> T.applySwipeToDismiss() {
        val dismissBehavior = SwipeDismissBehavior<T>().apply {
            setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
        }

        this.applyTouchBehavior(dismissBehavior)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun <T: View> T.applyTouchBehavior(behavior: CoordinatorLayout.Behavior<T>) {
        val parent = this.parent as CoordinatorLayout

        val dialogParams: CoordinatorLayout.LayoutParams =
                this.layoutParams as CoordinatorLayout.LayoutParams

        dialogParams.behavior = behavior

        this.setOnTouchListener { _, event ->
            behavior.onTouchEvent(parent, this, event)
        }
    }
}
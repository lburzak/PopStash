package com.github.polydome.popstash.app.feature.stash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.github.polydome.popstash.app.databinding.FragmentSaveFromClipboardBinding
import com.github.polydome.popstash.app.di.scope.BoundViewModel
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

        val rootView: CoordinatorLayout = binding.saveFromClipboardRoot
        val contentView: CardView = binding.saveFromClipboardContent

        val dialogParams: CoordinatorLayout.LayoutParams =
                contentView.layoutParams as CoordinatorLayout.LayoutParams

        val behavior = SwipeDismissBehavior<CardView>().apply {
            setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
            listener = object: SwipeDismissBehavior.OnDismissListener {
                override fun onDismiss(view: View?) {

                }

                override fun onDragStateChanged(state: Int) {

                }
            }
        }

        dialogParams.behavior = behavior

        binding.saveFromClipboardContent.setOnTouchListener { view, event ->
            behavior.onTouchEvent(rootView, contentView, event)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkClipboardForUrl()
    }

    private fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSaveFromClipboardBinding {
        return FragmentSaveFromClipboardBinding.inflate(inflater, container, false)
                .also {
                    it.lifecycleOwner = this
                    it.viewModel = viewModel
                }
    }
}
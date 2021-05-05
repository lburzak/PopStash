package com.github.polydome.popstash.app.feature.stash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.github.polydome.popstash.app.databinding.FragmentSaveFromClipboardBinding
import com.github.polydome.popstash.app.di.qualifier.BoundViewModel
import com.github.polydome.popstash.app.platform.view.DismissManager
import com.github.polydome.popstash.app.presentation.viewmodel.SaveFromClipboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SaveFromClipboardFragment : Fragment() {
    @Inject
    @BoundViewModel
    lateinit var viewModel: SaveFromClipboardViewModel

    private lateinit var binding: FragmentSaveFromClipboardBinding
    private lateinit var dismissManager: DismissManager<CardView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = createBinding(inflater, container)

        dismissManager = DismissManager.of(binding.saveFromClipboardContent)
        dismissManager.enableSwipeToDismiss()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.shouldDisplayDialog
                .observe(viewLifecycleOwner) { shouldDisplayDialog ->
                    if (shouldDisplayDialog && dismissManager.isDismissed)
                        dismissManager.restore()
                }

    }

    private fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSaveFromClipboardBinding {
        return FragmentSaveFromClipboardBinding.inflate(inflater, container, false)
                .also {
                    it.lifecycleOwner = this
                    it.viewModel = viewModel
                }
    }
}
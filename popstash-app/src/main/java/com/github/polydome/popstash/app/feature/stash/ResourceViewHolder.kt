package com.github.polydome.popstash.app.feature.stash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.polydome.popstash.app.databinding.RowResourceBinding
import com.github.polydome.popstash.app.feature.common.Swipeable
import com.github.polydome.popstash.app.presentation.viewmodel.ResourceViewModel
import javax.inject.Inject
import javax.inject.Provider

class ResourceViewHolder(
        private val binding: RowResourceBinding,
        private val viewModel: ResourceViewModel,
) : RecyclerView.ViewHolder(binding.root), Swipeable {
    init {
        binding.lifecycleOwner?.let { lifecycleOwner ->
            viewModel.loading.observe(lifecycleOwner) { isLoading ->
                if (isLoading)
                    binding.shimmer.showShimmer(false)
                else
                    binding.shimmer.hideShimmer()
            }

            viewModel.thumbnailUrl.observe(lifecycleOwner) { thumbnailUrl ->
                if (thumbnailUrl != null) {
                    binding.thumbnail.load(thumbnailUrl)
                } else {
                    binding.thumbnail.visibility = View.GONE
                }
            }
        }
    }

    fun onChangeUrl(url: String) {
        viewModel.showUrl(url)
    }

    override fun onSwiped() {
        viewModel.deleteCurrentResource()
    }

    class Factory @Inject constructor(private val layoutInflater: LayoutInflater,
                                      private val lifecycleOwner: LifecycleOwner,
                                      private val viewModelProvider: Provider<ResourceViewModel>) {
        fun create(parentView: ViewGroup): ResourceViewHolder {
            val viewModel = viewModelProvider.get()
            val binding = RowResourceBinding
                    .inflate(layoutInflater, parentView, false)
                    .apply {
                        lifecycleOwner = this@Factory.lifecycleOwner
                        viewmodel = viewModel
                    }
            return ResourceViewHolder(binding, viewModel)
        }
    }
}
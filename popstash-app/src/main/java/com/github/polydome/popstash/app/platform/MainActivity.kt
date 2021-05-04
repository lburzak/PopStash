package com.github.polydome.popstash.app.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.databinding.ActivityMainBinding
import com.github.polydome.popstash.app.di.scope.BoundViewModel
import com.github.polydome.popstash.app.feature.stash.StashAdapter
import com.github.polydome.popstash.app.platform.service.WindowEventListener
import com.github.polydome.popstash.app.presentation.service.Clipboard
import com.github.polydome.popstash.app.presentation.viewmodel.StashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var stashAdapter: StashAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    @BoundViewModel
    lateinit var stashViewModel: StashViewModel

    @Inject
    lateinit var windowEventListener: WindowEventListener

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupContentView()
        setupStashList()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        windowEventListener.onFocusChange(hasFocus)
    }

    private fun setupContentView() {
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .also {
                    it.viewModel = stashViewModel
                    it.lifecycleOwner = this
                }
    }

    private fun setupStashList() {
        binding.stashRecyclerView.apply {
            adapter = stashAdapter
            layoutManager = linearLayoutManager.apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }
}
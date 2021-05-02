package com.github.polydome.popstash.app.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.feature.stash.StashAdapter
import com.github.polydome.popstash.app.databinding.ActivityMainBinding
import com.github.polydome.popstash.app.di.scope.BoundViewModel
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
    lateinit var viewModel: StashViewModel

    private val stashRecyclerView: RecyclerView
        get() = findViewById(R.id.stash_recycler_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupContentView()
        setupStashList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkClipboardForUrl()
    }

    private fun setupContentView() {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .also {
                    it.viewModel = viewModel
                    it.lifecycleOwner = this
                }
    }

    private fun setupStashList() {
        stashRecyclerView.apply {
            adapter = stashAdapter
            layoutManager = linearLayoutManager.apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }
}
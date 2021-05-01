package com.github.polydome.popstash.app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.adapter.StashAdapter
import com.github.polydome.popstash.app.databinding.ActivityMainBinding
import com.github.polydome.popstash.app.di.BoundViewModel
import com.github.polydome.popstash.app.viewmodel.StashViewModel
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

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    viewModel = this@MainActivity.viewModel
                    lifecycleOwner = this@MainActivity
                }

        stashRecyclerView.apply {
            adapter = stashAdapter
            layoutManager = linearLayoutManager.apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        viewModel.isUrlInClipboard.observe(this) {
            Log.d("StashViewModel", "viewModel.isUrlInClipboard: %s".format(it))
        }

        viewModel.monitorClipboard()
    }
}
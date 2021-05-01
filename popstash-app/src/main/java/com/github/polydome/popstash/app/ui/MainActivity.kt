package com.github.polydome.popstash.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.adapter.StashAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject lateinit var stashAdapter: StashAdapter
    @Inject lateinit var linearLayoutManager: LinearLayoutManager

    private val stashRecyclerView: RecyclerView
        get() = findViewById(R.id.stash_recycler_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stashRecyclerView.apply {
            adapter = stashAdapter
            layoutManager = linearLayoutManager.apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }
}
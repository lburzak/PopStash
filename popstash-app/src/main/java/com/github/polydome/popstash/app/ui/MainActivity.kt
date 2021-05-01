package com.github.polydome.popstash.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.adapter.StashAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stashRecyclerView = findViewById<RecyclerView>(R.id.stash_recycler_view)
        stashRecyclerView.adapter = StashAdapter(LayoutInflater.from(this))
        stashRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }
}
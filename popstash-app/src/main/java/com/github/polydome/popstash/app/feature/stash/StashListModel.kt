package com.github.polydome.popstash.app.feature.stash

import javax.inject.Inject

class StashListModel @Inject constructor() {
    private val items = listOf(
            "http://google.com",
            "http://example.com",
            "http://medium.com"
        )

    fun itemAt(position: Int): String
        = items[position]

    fun countItems()
        = items.count()
}
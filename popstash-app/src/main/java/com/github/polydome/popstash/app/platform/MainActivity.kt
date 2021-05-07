package com.github.polydome.popstash.app.platform

import androidx.appcompat.app.AppCompatActivity
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.platform.service.WindowEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var windowEventListener: WindowEventListener

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        windowEventListener.onFocusChange(hasFocus)
    }
}
package com.github.polydome.popstash.app.platform

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.di.entrypoint.FragmentFactoryEntryPoint
import com.github.polydome.popstash.app.platform.service.WindowEventListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var windowEventListener: WindowEventListener

    private val fragmentFactoryEntryPoint: FragmentFactoryEntryPoint
        get() = EntryPointAccessors.fromActivity(this, FragmentFactoryEntryPoint::class.java)

    private val fragmentFactory: FragmentFactory
        get() = fragmentFactoryEntryPoint.fragmentFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        windowEventListener.onFocusChange(hasFocus)
    }
}
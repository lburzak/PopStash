package com.github.polydome.popstash.app.platform

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.di.entrypoint.FragmentFactoryEntryPoint
import com.github.polydome.popstash.app.platform.service.InternetBrowser
import com.github.polydome.popstash.app.platform.service.ThemeProvider
import com.github.polydome.popstash.app.platform.service.WindowEventListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), InternetBrowser {
    @Inject
    lateinit var windowEventListener: WindowEventListener

    @Inject
    lateinit var themeProvider: ThemeProvider

    private val fragmentFactoryEntryPoint: FragmentFactoryEntryPoint
        get() = EntryPointAccessors.fromActivity(this, FragmentFactoryEntryPoint::class.java)

    // TODO: Delegate
    private val fragmentFactory: FragmentFactory
        get() = fragmentFactoryEntryPoint.fragmentFactory()

    private fun preCreate() {
        supportFragmentManager.fragmentFactory = fragmentFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preCreate()

        super.onCreate(savedInstanceState)

        setupUI()
        listenForThemeChanges()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        windowEventListener.onFocusChange(hasFocus)
    }

    override fun browseSite(url: String) {
        val browserIntent = createBrowserIntent(url)
        startActivity(browserIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> showSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setTheme(themeProvider.getThemeResId())
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.app_bar))
    }

    private fun listenForThemeChanges() {
        lifecycleScope.launchWhenStarted {
            themeProvider.themeChanges
                    .collect { onThemeChanged() }
        }
    }

    private fun onThemeChanged() {
        recreate()
    }

    private fun createBrowserIntent(url: String): Intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))

    private fun showSettings() {
        findNavController(R.id.nav_container)
                .navigate(R.id.action_open_settings)
    }
}
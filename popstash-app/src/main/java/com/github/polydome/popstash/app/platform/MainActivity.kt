package com.github.polydome.popstash.app.platform

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.di.entrypoint.FragmentFactoryEntryPoint
import com.github.polydome.popstash.app.platform.service.InternetBrowser
import com.github.polydome.popstash.app.platform.settings.ThemeProvider
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

    private lateinit var navController: NavController

    private val fragmentFactory: FragmentFactory by lazy {
        EntryPointAccessors
                .fromActivity(this, FragmentFactoryEntryPoint::class.java)
                .fragmentFactory()
    }

    private fun preCreate() {
        supportFragmentManager.fragmentFactory = fragmentFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preCreate()

        super.onCreate(savedInstanceState)

        setupUI()

        listenForThemeChanges()
    }

    override fun onStart() {
        super.onStart()

        setupNavigation()
        setupAppBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        windowEventListener.onFocusChange(hasFocus)
    }

    override fun browseSite(url: String) {
        val browserIntent = createBrowserIntent(url)
        startActivity(browserIntent)
    }

    private fun setupUI() {
        setTheme(themeProvider.getThemeResId())
        setContentView(R.layout.activity_main)
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.nav_container)
    }

    private fun setupAppBar() {
        val configuration = AppBarConfiguration(navController.graph)
        val toolbar: Toolbar = findViewById(R.id.app_bar)

        toolbar.setupWithNavController(navController, configuration)

        toolbar.setOnMenuItemClickListener { item ->
            item.onNavDestinationSelected(navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.menu.findItem(R.id.destination_settings).isVisible =
                    destination.id != R.id.destination_settings
        }
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
}
package com.github.polydome.popstash.app.platform

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.di.entrypoint.FragmentFactoryEntryPoint
import com.github.polydome.popstash.app.di.entrypoint.ThemeProviderEntryPoint
import com.github.polydome.popstash.app.platform.service.InternetBrowser
import com.github.polydome.popstash.app.platform.service.ThemeProvider
import com.github.polydome.popstash.app.platform.service.WindowEventListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), InternetBrowser {
    @Inject
    lateinit var windowEventListener: WindowEventListener

    private val fragmentFactoryEntryPoint: FragmentFactoryEntryPoint
        get() = EntryPointAccessors.fromActivity(this, FragmentFactoryEntryPoint::class.java)

    private val fragmentFactory: FragmentFactory
        get() = fragmentFactoryEntryPoint.fragmentFactory()

    private val themeProviderEntryPoint: ThemeProviderEntryPoint
        get() = EntryPointAccessors.fromActivity(this, ThemeProviderEntryPoint::class.java)

    private val themeProvider: ThemeProvider
        get() = themeProviderEntryPoint.themeProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Try setting content view manually for proper easier injection
        setTheme(themeProvider.getThemeResId())

        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.app_bar))
        supportActionBar?.show()

        lifecycleScope.launchWhenStarted {
            themeProvider.themeChanges
                    .onEach { Log.d("#jk8", "new theme = $it") }
                    .collect {
                        recreate()
                    }
        }
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        windowEventListener.onFocusChange(hasFocus)
    }

    override fun browseSite(url: String) {
        val browserIntent = createBrowserIntent(url)
        startActivity(browserIntent)
    }

    private fun createBrowserIntent(url: String): Intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))

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

    private fun showSettings() {
        findNavController(R.id.nav_container)
                .navigate(R.id.action_open_settings)
    }
}
package com.github.polydome.popstash.app.platform

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentFactory
import androidx.navigation.findNavController
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.di.entrypoint.FragmentFactoryEntryPoint
import com.github.polydome.popstash.app.platform.service.InternetBrowser
import com.github.polydome.popstash.app.platform.service.WindowEventListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), InternetBrowser {
    @Inject
    lateinit var windowEventListener: WindowEventListener

    private val fragmentFactoryEntryPoint: FragmentFactoryEntryPoint
        get() = EntryPointAccessors.fromActivity(this, FragmentFactoryEntryPoint::class.java)

    private val fragmentFactory: FragmentFactory
        get() = fragmentFactoryEntryPoint.fragmentFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.app_bar))
        supportActionBar?.show()
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
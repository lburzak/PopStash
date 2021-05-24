package com.github.polydome.popstash.app.feature

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.polydome.popstash.app.R
import com.github.polydome.popstash.app.presentation.viewmodel.SaveResourceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SaveLinkActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: SaveResourceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = if (intent.shouldBeHandled())
            intent.getSharedText()
        else null

        if (text != null)
            saveLink(text)
        else
            showActionRejected()

        finish()
    }

    private fun showActionRejected() {
        Toast.makeText(this, getString(R.string.resource_unable_to_save), Toast.LENGTH_LONG)
                .show()
    }

    private fun saveLink(url: String) {
        lifecycleScope.launchWhenStarted {
            viewModel.url.emit(url)
        }
    }

    private fun Intent?.shouldBeHandled(): Boolean =
            this != null && intent.isShareIntent()

    private fun Intent.getSharedText(): String? =
            getStringExtra(Intent.EXTRA_TEXT)

    private fun Intent.isShareIntent(): Boolean =
            action == Intent.ACTION_SEND
}
package com.github.polydome.popstash.app.platform.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.polydome.popstash.app.R

class AttributionsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_attributions, container, false)
        val webView: WebView = view.findViewById(R.id.web_view)

        lifecycleScope.launchWhenStarted {
            webView.loadData(getText(R.string.attributions).toString(), "text/html", "UTF-8")
        }

        return view
    }
}
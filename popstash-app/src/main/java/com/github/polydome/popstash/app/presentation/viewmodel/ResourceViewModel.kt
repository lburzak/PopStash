package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.app.presentation.common.InternetBrowser
import com.github.polydome.popstash.domain.usecase.DeleteResource
import com.github.polydome.popstash.domain.usecase.IdentifyResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResourceViewModel @Inject constructor(
        private val identifyResource: IdentifyResource,
        private val deleteResource: DeleteResource,
        private val internetBrowser: InternetBrowser
) : ViewModel() {
    private val _title = MutableLiveData<String>()
    private val _site = MutableLiveData<String>()
    private val _loading = MutableLiveData<Boolean>()
    private val _thumbnailUrl = MutableLiveData<String?>()

    val title: LiveData<String> = _title
    val site: LiveData<String> = _site
    val loading: LiveData<Boolean> = _loading
    val thumbnailUrl: LiveData<String?> = _thumbnailUrl

    private var resourceUrl: String? = null

    fun showUrl(url: String) {
        viewModelScope.launch {
            _loading.postValue(true)

            val identificationResult = identifyResource.execute(url)

            var title = ""
            var site = ""
            var thumbnailUrl: String? = null

            if (identificationResult is IdentifyResource.Result.Success) {
                resourceUrl = url
                with(identificationResult.metadata) {
                    title = this.title
                    site = this.site
                    thumbnailUrl = this.thumbnailUrl
                }
            }

            _title.postValue(title)
            _site.postValue(site)
            _thumbnailUrl.postValue(thumbnailUrl)

            _loading.postValue(false)
        }
    }

    fun deleteCurrentResource() {
        resourceUrl?.let { url ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    deleteResource.execute(url)
                }
            }
        }
    }

    fun openCurrentResource() {
        resourceUrl?.let { url ->
            internetBrowser.browseSite(url)
        }
    }
}

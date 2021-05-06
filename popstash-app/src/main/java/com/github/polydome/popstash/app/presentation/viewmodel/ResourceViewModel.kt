package com.github.polydome.popstash.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.polydome.popstash.domain.usecase.IdentifyResource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResourceViewModel @Inject constructor(
        private val identifyResource: IdentifyResource
) : ViewModel() {
    private val _title = MutableLiveData<String>()
    private val _url = MutableLiveData<String>()

    val title: LiveData<String> = _title
    val url: LiveData<String> = _url

    fun showUrl(url: String) {
        viewModelScope.launch {
            val identificationResult = identifyResource.execute(url)

            var title = ""
            var site = ""

            if (identificationResult is IdentifyResource.Result.Success) {
                with(identificationResult.metadata) {
                    title = this.title
                    site = this.site
                }
            }

            _title.postValue(title)
            _url.postValue(site)
        }
    }
}

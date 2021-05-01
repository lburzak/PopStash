package com.github.polydome.popstash.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ResourceViewModel : ViewModel() {
    private val _title = MutableLiveData<String>()
    private val _url = MutableLiveData<String>()

    val title: LiveData<String> = _title
    val url: LiveData<String> = _url

    fun showUrl(url: String) {
        viewModelScope.launch {
            _title.postValue(url)
            _url.postValue(url)
        }
    }
}

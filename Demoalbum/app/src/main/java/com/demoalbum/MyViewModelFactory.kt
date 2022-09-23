package com.demoalbum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demoalbum.network.ApiService

class MyViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ApiService::class.java).newInstance(apiService)
    }
}
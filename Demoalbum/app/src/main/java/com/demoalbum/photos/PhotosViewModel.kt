package com.demoalbum.photos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demoalbum.Coroutines
import com.demoalbum.NoInternetException
import com.demoalbum.network.ApiService
import java.lang.Exception

class PhotosViewModel(
    private val apiService: ApiService
) : ViewModel() {
    var progressDialog = MutableLiveData<Boolean>()
    var isNetworkError = MutableLiveData<Boolean>()
    var isApiException = MutableLiveData<Boolean>()
    var imagesList = MutableLiveData<List<PhotosData>?>()

    fun getListOfImages() {
        progressDialog.postValue(true)

        Coroutines.main {
            try {
                val response =
                    apiService.getPhotos()
                progressDialog.postValue(false)
                if (response.isSuccessful) {
                    val myObj: List<PhotosData>? = response.body()
                    imagesList.postValue(myObj)
                } else {
                    Log.e("msg", "" + response.errorBody())
                    isApiException.postValue(true)
                }
            } catch (e: NoInternetException) {
                progressDialog.postValue(false)
                isNetworkError.postValue(true)
            } catch (e: Exception) {
                progressDialog.postValue(false)
                isApiException.postValue(true)
            }

        }

    }

}
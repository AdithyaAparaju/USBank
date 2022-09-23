package com.demoalbum.network

import androidx.annotation.Keep
import com.demoalbum.album.AlbumData
import com.demoalbum.photos.PhotosData
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

@Keep
interface ApiService {

    @GET("albums?")
    suspend fun getAlbums(): Response<List<AlbumData>>

    @GET("photos?")
    suspend fun getPhotos(): Response<List<PhotosData>>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiService {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}

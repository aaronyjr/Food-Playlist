package com.example.pandacapstone.model.service

import com.example.pandacapstone.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private val okHttp3Client = OkHttpClient.Builder()

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        if(BuildConfig.DEBUG){
            okHttp3Client.addInterceptor(logging)
        }
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp3Client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val playlistService: PlaylistService by lazy {
        retrofit.create(PlaylistService::class.java)
    }
}

package com.example.lol_project

import com.example.lol_project.api.ApiService
import com.example.lol_project.utils.Constants.Companion.BaseURL
import com.example.lol_project.utils.Constants.Companion.PersonalApiKey
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Creation of an instance of OkHttpClient, to make HTTP request
    private val client = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val original: Request = chain.request()
            val request: Request = original.newBuilder()
                .header("X-Riot-Token", PersonalApiKey)
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        addInterceptor(loggingInterceptor)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val retrofitService = buildService(ApiService::class.java)

    private fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun getRetrofitService(): ApiService {
        return retrofitService
    }
}

package com.dias.kisahnabiapp.data.network

import com.dias.kisahnabiapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    fun getApiService(): ApiService {

        // Create an OkHttpClient to be able to make a log of the network traffic
        val httpLoggingInterceptor: HttpLoggingInterceptor = if (BuildConfig.DEBUG){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                return@addInterceptor it.proceed(request)
            }
            // ping every 10 seconds
            .pingInterval(10, TimeUnit.SECONDS)
            // timeout after 30 seconds
            .readTimeout(30, TimeUnit.SECONDS)
            // count as timeout after 30 seconds
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        // used to create the retrofit instance and implement ApiService
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}
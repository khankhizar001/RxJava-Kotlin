package com.example.appiness.rxjavakotlin.service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by appiness on 27/4/18.
 */

object RetrofitService {

    private lateinit var retrofit: Retrofit
    private var okHttpClient: OkHttpClient? = null
    private val REQUEST_TIMEOUT = 60
    val client: Retrofit
        get() {
            if (RetrofitService.okHttpClient == null)
                initOkHttp()

            retrofit = Retrofit.Builder()
                        .baseUrl("https://api.androidhive.info/json/")
                        .client(okHttpClient)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()


            return retrofit
        }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(RetrofitService.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(RetrofitService.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(RetrofitService.REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Request-Type", "Android")
                    .addHeader("Content-Type", "application/json")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        RetrofitService.okHttpClient = httpClient.build()
    }

    fun resetApiClient() {
        RetrofitService.okHttpClient = null
    }
}

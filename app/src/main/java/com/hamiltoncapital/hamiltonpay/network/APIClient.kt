package com.hamiltoncapital.hamiltonpay.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        fun create(): APIService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addNetworkInterceptor{ chain ->
                    val request = chain.request()
                    request.newBuilder().addHeader("x-api-key", "1U9CeisPCnXgoZUOJqsDN0KDHY3FkXwvw1Lgu2BPTiw").build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(URL.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }
}
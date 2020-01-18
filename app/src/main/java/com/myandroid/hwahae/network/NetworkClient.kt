package com.myandroid.hwahae.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {

    companion object {
        fun getClient(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor).build()

            return Retrofit.Builder()
                .baseUrl("https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

    }
}
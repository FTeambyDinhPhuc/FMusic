package com.example.fmusic.service_api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIRetrofitClient {

     fun getClient(base_url: String): Retrofit
    {
        val okHttpClient = OkHttpClient()
        okHttpClient.retryOnConnectionFailure()
        val gson = GsonBuilder().setLenient().create();
        val retrofit =  Retrofit.Builder()
            .baseUrl(base_url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
        return retrofit
    }
}
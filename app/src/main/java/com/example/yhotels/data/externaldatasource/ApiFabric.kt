package com.example.yhotels.data.externaldatasource

import com.example.yhotels.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun getApiInterface(): ApiInterface{

    val builder = Retrofit.Builder()
        .baseUrl(Constants.API_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val httpClient = OkHttpClient.Builder()
        .readTimeout(60,TimeUnit.SECONDS)
        .connectTimeout(60,TimeUnit.SECONDS)

    return builder.client(httpClient.build()).build().create(ApiInterface::class.java)
}
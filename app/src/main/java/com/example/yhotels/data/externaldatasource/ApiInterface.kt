package com.example.yhotels.data.externaldatasource

import com.example.yhotels.data.entities.HotelDTO
import com.example.yhotels.data.entities.HotelDetailsDTO
import com.example.yhotels.presentation.entities.HotelDetails
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("0777.json")
    suspend fun getHotels(): Response<List<HotelDTO>>

    @GET("{id}.json")
    suspend fun getHotelDetails(@Path("id") id:Int):Response<HotelDetailsDTO>

}
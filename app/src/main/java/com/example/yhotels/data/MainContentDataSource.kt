package com.example.yhotels.data

import com.example.yhotels.data.entities.HotelDTO
import com.example.yhotels.data.entities.HotelDetailsDTO
import retrofit2.Response

interface MainContentDataSource {

    suspend fun getHotelsList():Response<List<HotelDTO>>
    suspend fun getHotelById(id:Int):Response<HotelDetailsDTO>
}
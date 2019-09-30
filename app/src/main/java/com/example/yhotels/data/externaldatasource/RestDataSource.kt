package com.example.yhotels.data.externaldatasource

import com.example.yhotels.data.MainContentDataSource
import com.example.yhotels.data.entities.HotelDTO
import com.example.yhotels.data.entities.HotelDetailsDTO
import com.example.yhotels.presentation.entities.HotelDetails
import retrofit2.Response

class RestDataSource:MainContentDataSource{

    override suspend fun getHotelsList():Response<List<HotelDTO>> {
        return getApiInterface().getHotels()
    }

    override suspend fun getHotelById(id:Int):Response<HotelDetailsDTO> {
        return getApiInterface().getHotelDetails(id)
    }
}
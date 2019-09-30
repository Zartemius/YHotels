package com.example.yhotels.data.internaldatasource

import com.example.yhotels.data.HotelInfoDataSource

class HotelInfoStorage:HotelInfoDataSource {

    private var id:Int? = 0

    override fun getHotelId(): Int? {
        return id
    }

    override fun setHotelId(hotelId: Int) {
        id = hotelId
    }
}
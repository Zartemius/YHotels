package com.example.yhotels.data.entities

import com.google.gson.annotations.SerializedName

class HotelDetailsDTO {

    @SerializedName("id")
    val id:Int? = null
    @SerializedName("name")
    val name:String? = null
    @SerializedName("address")
    val address:String? = null
    @SerializedName("stars")
    val stars:Double? = null
    @SerializedName("distance")
    val distance:Double? = null
    @SerializedName("suites_availability")
    val suitesAvailability:String? = null
    @SerializedName("image")
    val image:String? = null
    @SerializedName("lat")
    val lat:Double? = null
    @SerializedName("lon")
    val lon:Double? = null
}
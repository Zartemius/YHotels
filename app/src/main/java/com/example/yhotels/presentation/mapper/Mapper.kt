package com.example.yhotels.presentation.mapper

import com.example.yhotels.common.Constants
import com.example.yhotels.presentation.entities.Hotel
import com.example.yhotels.data.entities.HotelDTO
import com.example.yhotels.data.entities.HotelDetailsDTO
import com.example.yhotels.presentation.entities.HotelDetails

class Mapper{

    companion object {
        private fun mapHotel(hotelDTO: HotelDTO): Hotel {
            val hotel = Hotel()

            hotel.id = hotelDTO.id
            hotel.name = hotelDTO.name
            hotel.address = hotelDTO.address
            hotel.stars = hotelDTO.stars
            hotel.distance = hotelDTO.distance
            hotel.suites =
                getSuites(hotelDTO.suitesAvailability)
            return hotel
        }

        private fun getSuites(availableSuites:String?):ArrayList<Int>{
            val receivedString = availableSuites?.split(":")?.map { it.trim()} as ArrayList
            receivedString.remove("")
            val finalResult = ArrayList<Int>()
            for(i in receivedString){
                finalResult.add(i.toInt())
            }
            return finalResult
        }

        private fun getImageUrl(imageId:String?):String{
            val baseUrl = Constants.IMAGE_URL
            val urlBuilder = StringBuilder()
            urlBuilder.append(baseUrl).append(imageId)
            val imageUrl = urlBuilder.toString()
            return imageUrl
        }

        fun mapHotelDetails(hotelDetailsDTO: HotelDetailsDTO?):HotelDetails{
            val hotelDetails = HotelDetails()

            if(hotelDetailsDTO?.name != null) {
                hotelDetails.name = hotelDetailsDTO.name
            }
            if(hotelDetailsDTO?.address != null){
                hotelDetails.address = hotelDetailsDTO.address
            }
            hotelDetails.id = hotelDetailsDTO?.id

            if(hotelDetailsDTO?.distance != null) {
                hotelDetails.distance = hotelDetailsDTO.distance.toString()
            }

            if(hotelDetailsDTO?.image != null) {
                if (hotelDetailsDTO.image.isNotEmpty()) {
                    hotelDetails.imageIsAvailable = true
                    hotelDetails.imageUrl = getImageUrl(hotelDetailsDTO.image)
                }
            }

            if(hotelDetailsDTO?.stars != null) {
                hotelDetails.stars = hotelDetailsDTO.stars.toString()
            }
            if(hotelDetailsDTO?.suitesAvailability !=null) {
                hotelDetails.suites = getSuites(hotelDetailsDTO.suitesAvailability).toString()
            }
            hotelDetails.lat = hotelDetailsDTO?.lat
            hotelDetails.lon = hotelDetailsDTO?.lon

            return hotelDetails
        }

        fun mapHotelsList(hotelsDTO:List<HotelDTO>): ArrayList<Hotel> {
            val hotels: ArrayList<Hotel> = ArrayList<Hotel>()

            for (hotel in hotelsDTO) {
                val mappedHotel = mapHotel(hotel)
                hotels.add(mappedHotel)
            }
            return hotels
        }
    }
}
package com.example.yhotels.presentation.screens.hoteldetailsscreen

import com.example.yhotels.presentation.BaseView
import com.example.yhotels.presentation.entities.HotelDetails

interface HotelDetailsScreenContract:BaseView {

    fun isInternetConnectionActive():Boolean?

    fun loadPicture(imageIsAvailable:Boolean?,pictureUrl:String?)

    fun loadMainContentInViews(hotelDetails: HotelDetails)

    fun callMapApp(latitude:Double, longitude:Double)

    fun showNoInternetWarning()

    fun setViewsReadyForDisplaying()

    fun showProgressBar()

    fun hideProgressBar()
}
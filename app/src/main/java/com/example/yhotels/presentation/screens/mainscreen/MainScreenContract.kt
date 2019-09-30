package com.example.yhotels.presentation.screens.mainscreen

import com.example.yhotels.presentation.entities.Hotel
import com.example.yhotels.presentation.BaseView

interface MainScreenContract: BaseView {

    fun showProgressBar()

    fun hideProgressBar()

    fun showNoInternetWarning()

    fun isInternetConnectionActive():Boolean?

    fun setHotelsList(hotels:ArrayList<Hotel>)

    fun updateHotelsList(hotels: ArrayList<Hotel>)

    fun hideRefreshingProgressBar()

    fun setFilterButtonIsEnabled()

    fun setRecyclerViewPreparedForLoading()
}
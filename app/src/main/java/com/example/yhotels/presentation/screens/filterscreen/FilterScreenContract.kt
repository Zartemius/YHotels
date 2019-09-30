package com.example.yhotels.presentation.screens.filterscreen

import com.example.yhotels.presentation.BaseView

interface FilterScreenContract:BaseView {

    fun initSwitches(sortingByDistanceDistanceWasChosen:Boolean, sortingByRoomsWasChosen:Boolean)
}
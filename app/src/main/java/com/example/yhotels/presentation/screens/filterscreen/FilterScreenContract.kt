package com.example.yhotels.presentation.screens.filterscreen

import com.example.yhotels.presentation.BaseView
import com.example.yhotels.presentation.entities.FilterSettings

interface FilterScreenContract:BaseView {

    fun initSwitches(sortingByDistanceDistanceWasChosen:Boolean, sortingByRoomsWasChosen:Boolean)

    fun returnCurrentSettings(filterSettings: FilterSettings)
}
package com.example.yhotels.data

interface FilterSettingsDataSource {

    fun setSortingByDistanceSettingIsActive(settingIsActive:Boolean)

    fun setSortingByRoomsSettingIsActive(settingIsActive:Boolean)

    fun sortingByDistanceSettingIsActive():Boolean

    fun sortingByRoomsSettingIsActive():Boolean

    fun clearSettings()

    fun settingsWereChanged():Boolean

    fun setSettingsCurrentState(settingsWereChanged:Boolean)
}
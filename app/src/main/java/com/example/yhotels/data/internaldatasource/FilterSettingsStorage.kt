package com.example.yhotels.data.internaldatasource

import com.example.yhotels.data.FilterSettingsDataSource

class FilterSettingsStorage:FilterSettingsDataSource {

    private var settingStateWasChanged:Boolean = false
    private var sortingByDistanceSettingIsActive:Boolean = false
    private var sortingByRoomsSettingsIsActive:Boolean = false

    override fun setSortingByDistanceSettingIsActive(settingIsActive: Boolean) {
        sortingByDistanceSettingIsActive = settingIsActive
    }

    override fun setSortingByRoomsSettingIsActive(settingIsActive: Boolean) {
       sortingByRoomsSettingsIsActive = settingIsActive
    }

    override fun sortingByRoomsSettingIsActive(): Boolean {
        return sortingByRoomsSettingsIsActive
    }

    override fun sortingByDistanceSettingIsActive(): Boolean {
        return sortingByDistanceSettingIsActive
    }

    override fun clearSettings() {
        sortingByDistanceSettingIsActive = false
        sortingByRoomsSettingsIsActive = false
    }

    override fun settingsWereChanged():Boolean {
        return settingStateWasChanged
    }

    override fun setSettingsCurrentState(settingsWereChanged: Boolean) {
        settingStateWasChanged = settingsWereChanged
    }
}
package com.example.yhotels.presentation.entities

import android.os.Parcel
import android.os.Parcelable

class FilterSettings:Parcelable {

    private var settingStateWasChanged:Boolean = false
    private var sortingByDistanceSettingIsActive:Boolean = false
    private var sortingByRoomsSettingsIsActive:Boolean = false

    constructor(

    )

    constructor(
        settingStateWasChanged: Boolean,
        sortingByDistanceSettingIsActive: Boolean,
        sortingByRoomsSettingsIsActive: Boolean) {
        this.settingStateWasChanged = settingStateWasChanged
        this.sortingByDistanceSettingIsActive = sortingByDistanceSettingIsActive
        this.sortingByRoomsSettingsIsActive = sortingByRoomsSettingsIsActive
    }

    fun setSortingByDistanceSettingIsActive(settingIsActive: Boolean) {
        sortingByDistanceSettingIsActive = settingIsActive
    }

    fun setSortingByRoomsSettingIsActive(settingIsActive: Boolean) {
        sortingByRoomsSettingsIsActive = settingIsActive
    }

    fun sortingByRoomsSettingIsActive(): Boolean {
        return sortingByRoomsSettingsIsActive
    }

    fun sortingByDistanceSettingIsActive(): Boolean {
        return sortingByDistanceSettingIsActive
    }

    fun clearSettings() {
        sortingByDistanceSettingIsActive = false
        sortingByRoomsSettingsIsActive = false
    }

    fun settingsWereChanged():Boolean {
        return settingStateWasChanged
    }

    fun setSettingsCurrentState(settingsWereChanged: Boolean) {
        settingStateWasChanged = settingsWereChanged
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, p1: Int) {
        dest?.writeInt(if (settingStateWasChanged) 1 else 0)
        dest?.writeInt(if (sortingByDistanceSettingIsActive) 1 else 0)
        dest?.writeInt(if (sortingByRoomsSettingsIsActive) 1 else 0)
    }

    private constructor(parcel: Parcel):this(
        settingStateWasChanged = parcel.readInt() == 1,
        sortingByDistanceSettingIsActive = parcel.readInt() == 1,
        sortingByRoomsSettingsIsActive = parcel.readInt() == 1
    )

    companion object{
        @JvmField
        val CREATOR = object : Parcelable.Creator<FilterSettings>{

            override fun createFromParcel(parcel: Parcel): FilterSettings {
                return FilterSettings(parcel)
            }

            override fun newArray(size:Int): Array<FilterSettings?> {
                return arrayOfNulls<FilterSettings>(size)
            }
        }
    }
}
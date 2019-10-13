package com.example.yhotels.presentation.screens.filterscreen

import com.example.yhotels.presentation.BasePresenter
import com.example.yhotels.presentation.entities.FilterSettings
import javax.inject.Inject

class FilterScreenPresenter@Inject constructor()
    :BasePresenter<FilterScreenContract>(){

    private var mSortingByDistanceSettingIsSet = false
    private var mSortingByRoomsSettingIsSet = false
    private var mInitialInitializationIsDone = false
    private lateinit var mFilterSettings: FilterSettings

    private fun setFilterSettings(){

        val sortingByDistanceSettingIsActive:Boolean
        val sortingByRoomsSettingIsActive:Boolean

        if(::mFilterSettings.isInitialized && !mInitialInitializationIsDone) {
            sortingByDistanceSettingIsActive = mFilterSettings.sortingByDistanceSettingIsActive()
            sortingByRoomsSettingIsActive = mFilterSettings.sortingByRoomsSettingIsActive()
            mInitialInitializationIsDone = true
        }else{
            sortingByDistanceSettingIsActive = mSortingByDistanceSettingIsSet
            sortingByRoomsSettingIsActive = mSortingByRoomsSettingIsSet
        }

        view?.initSwitches(sortingByDistanceSettingIsActive,sortingByRoomsSettingIsActive)
    }

    fun setSortingByDistance(switchIsChecked:Boolean){
        mSortingByDistanceSettingIsSet = switchIsChecked
    }

    fun setSortingByRooms(switchIsChecked: Boolean){
        mSortingByRoomsSettingIsSet = switchIsChecked
    }

    override fun takeView(view: FilterScreenContract) {
        super.takeView(view)
        setFilterSettings()
    }

    fun applySettings(){
        if(::mFilterSettings.isInitialized) {
            mFilterSettings.setSortingByDistanceSettingIsActive(mSortingByDistanceSettingIsSet)
            mFilterSettings.setSortingByRoomsSettingIsActive(mSortingByRoomsSettingIsSet)
            mFilterSettings.setSettingsCurrentState(true)
        }
    }

    private fun clearUnsavedSettings(){
        if(mSortingByDistanceSettingIsSet){
            mSortingByDistanceSettingIsSet = false
        }

        if(mSortingByRoomsSettingIsSet){
            mSortingByRoomsSettingIsSet = false
        }
        mInitialInitializationIsDone = false
    }

    fun processReturningToPreviousScreen(){
        clearUnsavedSettings()
        view?.returnCurrentSettings(mFilterSettings)
    }

    fun setFilterSettings(filterSettings: FilterSettings){
        mFilterSettings = filterSettings
    }
}
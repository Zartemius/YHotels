package com.example.yhotels.presentation.screens.filterscreen

import com.example.yhotels.presentation.BasePresenter
import com.example.yhotels.presentation.entities.FilterSettings
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FilterScreenPresenter@Inject constructor(router: Router)
    :BasePresenter<FilterScreenContract>(){

    private val mRouter = router
    private var mSortingByDistanceSettingIsSet = false
    private var mSortingByRoomsSettingIsSet = false
    private var initialInitializationIsDone = false
    private lateinit var mFilterSettings: FilterSettings

    private fun setFilterSettings(){

        val sortingByDistanceSettingIsActive:Boolean
        val sortingByRoomsSettingIsActive:Boolean

        if(::mFilterSettings.isInitialized && !initialInitializationIsDone) {
            sortingByDistanceSettingIsActive = mFilterSettings.sortingByDistanceSettingIsActive()
            sortingByRoomsSettingIsActive = mFilterSettings.sortingByRoomsSettingIsActive()
            initialInitializationIsDone = true
        }else{
            sortingByDistanceSettingIsActive = mSortingByDistanceSettingIsSet
            sortingByRoomsSettingIsActive = mSortingByRoomsSettingIsSet
        }

        view?.initSwitches(sortingByDistanceSettingIsActive,sortingByRoomsSettingIsActive)
    }

    fun setSortingByDistance(switchIsChecked:Boolean){
        mSortingByDistanceSettingIsSet = switchIsChecked
        //        //Log.i("SORTING", "BY_DASTANCE " + mSortingByDistanceWasChosen)
    }

    fun setSortingByRooms(switchIsChecked: Boolean){
        mSortingByRoomsSettingIsSet = switchIsChecked
        //Log.i("SORTING", "BY_ROOMS " + mSortingByRoomsWasChosen)
    }

    override fun takeView(view: FilterScreenContract) {
        super.takeView(view)

        //Log.i("SORTING", "BY_ROOMS " + mSortingByRoomsWasChosen)
        setFilterSettings()
    }

    fun applySettings(){
        if(::mFilterSettings.isInitialized) {
            mFilterSettings.setSortingByDistanceSettingIsActive(mSortingByDistanceSettingIsSet)
            mFilterSettings.setSortingByRoomsSettingIsActive(mSortingByRoomsSettingIsSet)
            mFilterSettings.setSettingsCurrentState(true)
        }
        //mRouter.exit()
    }

    private fun clearUnsavedSettings(){
        if(mSortingByDistanceSettingIsSet){
            mSortingByDistanceSettingIsSet = false
        }

        if(mSortingByRoomsSettingIsSet){
            mSortingByRoomsSettingIsSet = false
        }
        initialInitializationIsDone = false
    }

    fun processReturningToPreviousScreen(){
        clearUnsavedSettings()
        mRouter.exit()
        view?.returnCurrentSettings(mFilterSettings)

    }

    fun setFilterSettings(filterSettings: FilterSettings){
        mFilterSettings = filterSettings
    }
}
package com.example.yhotels.presentation.screens.filterscreen

import com.example.yhotels.data.FilterSettingsDataSource
import com.example.yhotels.presentation.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FilterScreenPresenter@Inject constructor(router: Router,
                                               filterSettingsDataSource: FilterSettingsDataSource)
    :BasePresenter<FilterScreenContract>(){

    private val mRouter = router
    private val mFilterSettingsDataSource = filterSettingsDataSource
    private var mSortingByDistanceSettingIsSet = false
    private var mSortingByRoomsSettingIsSet = false
    private var initialInitializationIsDone = false

    fun navigateToPreviousScreen(){
        clearUnsavedSettings()
        mRouter.exit()
    }

    private fun setFilterSettings(){

        val sortingByDistanceSettingIsActive:Boolean
        val sortingByRoomsSettingIsActive:Boolean

        if(initialInitializationIsDone){
            sortingByDistanceSettingIsActive = mSortingByDistanceSettingIsSet
            sortingByRoomsSettingIsActive = mSortingByRoomsSettingIsSet
        }else{
            sortingByDistanceSettingIsActive = mFilterSettingsDataSource.sortingByDistanceSettingIsActive()
            sortingByRoomsSettingIsActive = mFilterSettingsDataSource.sortingByRoomsSettingIsActive()
            initialInitializationIsDone = true
        }
        //Log.i("SORTING", "VIEW " + view)
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
        mFilterSettingsDataSource.setSortingByDistanceSettingIsActive(mSortingByDistanceSettingIsSet)
        mFilterSettingsDataSource.setSortingByRoomsSettingIsActive(mSortingByRoomsSettingIsSet)
        mFilterSettingsDataSource.setSettingsCurrentState(true)
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
}
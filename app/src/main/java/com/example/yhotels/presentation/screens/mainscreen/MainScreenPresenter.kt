package com.example.yhotels.presentation.screens.mainscreen

import com.example.yhotels.data.FilterSettingsDataSource
import com.example.yhotels.data.HotelInfoDataSource
import com.example.yhotels.data.MainContentDataSource
import com.example.yhotels.presentation.entities.Hotel
import com.example.yhotels.data.entities.HotelDTO
import com.example.yhotels.navigation.Screens
import com.example.yhotels.presentation.BasePresenter
import com.example.yhotels.presentation.mapper.Mapper
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import ru.terrakok.cicerone.Router
import java.io.IOException
import java.lang.NullPointerException
import javax.inject.Inject

class MainScreenPresenter@Inject constructor(router: Router,filterSettingsDataSource: FilterSettingsDataSource,
                                             mainContentDataSource: MainContentDataSource,
                                             hotelInfoDataSource: HotelInfoDataSource)
    :BasePresenter<MainScreenContract>() {

    private val mRouter = router
    private val mFilterSettingsDataSource = filterSettingsDataSource
    private val mHotelInfoDataSource = hotelInfoDataSource
    private val mMainContentDataSource = mainContentDataSource
    private var mHotelsList = ArrayList<Hotel>()

    private fun loadHotels(listWasRefreshed:Boolean){
            launch {
                        try {
                                val response = getHotelsList()
                                withContext(Dispatchers.Main){
                                        if(response.isSuccessful){
                                            mHotelsList =
                                                Mapper.mapHotelsList(response.body()!!)
                                            view?.setRecyclerViewPreparedForLoading()
                                            view?.setHotelsList(mHotelsList)
                                            view?.setFilterButtonIsEnabled()
                                            hideProgressBar(listWasRefreshed)
                                        }
                                }
                        }catch(e:HttpException){

                        }catch (e:IOException){
                            view?.showNoInternetWarning()
                        }catch (e:Throwable){

                        }
            }
    }

    private fun updateHotelsList(){
            launch {
                try {
                    val response = getHotelsList()
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            mHotelsList =
                                Mapper.mapHotelsList(response.body()!!)
                            applyFilterSettings(mHotelsList, true)
                            view?.setRecyclerViewPreparedForLoading()
                            view?.updateHotelsList(mHotelsList)
                            hideProgressBar(true)
                        }
                    }
                } catch (e: HttpException) {

                } catch (e: IOException) {
                    view?.showNoInternetWarning()
                } catch (e: Throwable) {

                }
            }
    }

    private fun hideProgressBar(refreshProgressBarIsActive:Boolean){
            if(refreshProgressBarIsActive){
                view?.hideRefreshingProgressBar()
            }else{
                view?.hideProgressBar()
            }
    }

    private fun initHotelsList(){
        if(mHotelsList.isEmpty()) {
            try {
                if (view?.isInternetConnectionActive()!!) {
                    view?.showProgressBar()
                    loadHotels(false)
                } else {
                    view?.showNoInternetWarning()
                }
            }catch (e:NullPointerException){
                e.printStackTrace()
            }
        }else{
            view?.setFilterButtonIsEnabled()
            view?.setRecyclerViewPreparedForLoading()
            setHotelsListUsingCachedData(mHotelsList)
        }
    }

    fun applyUpdatesToHotelsList(){
        try {
                if(view?.isInternetConnectionActive()!!) {
                    if(mHotelsList.isEmpty()){
                        loadHotels(true)
                    }else{
                        updateHotelsList()
                    }

                }else{
                    view?.hideRefreshingProgressBar()
                    view?.showNoInternetWarning()
                }
        }catch (e:NullPointerException){
            e.printStackTrace()
        }
    }

    private suspend fun getHotelsList():Response<List<HotelDTO>>{
        return mMainContentDataSource.getHotelsList()
    }

    fun navigateToFilterScreen(){
        mRouter.navigateTo(Screens.FilterScreenDestination())
    }

    fun navigateToHotelDetailsScreen(){
        mRouter.navigateTo(Screens.HotelDetailsDestination())
    }

    private fun sortHotelsByAvailableRooms(hotels:ArrayList<Hotel>){
        hotels.sortByDescending{it.suites?.size}
    }

    private fun sortHotelsByByDistanceFromCenter(hotels:ArrayList<Hotel>){
        hotels.sortBy{ it.distance}
    }

    private fun saveChosenHotelId(id:Int){
        mHotelInfoDataSource.setHotelId(id)
    }

    override fun takeView(view: MainScreenContract) {
        super.takeView(view)
        initHotelsList()
    }

    private fun setHotelsListUsingCachedData(hotelsList:ArrayList<Hotel>){
        applyFilterSettings(hotelsList,false)
        view?.setHotelsList(hotelsList)
    }

    private fun applyFilterSettings(hotelsList:ArrayList<Hotel>,
                                    sortingMustBeDoneForUpdatedList:Boolean){
        val sortingByDistanceSettingIsActive = mFilterSettingsDataSource.sortingByDistanceSettingIsActive()
        val sortingByRoomsSettingIsActive = mFilterSettingsDataSource.sortingByRoomsSettingIsActive()
        val settingsWereChanged = mFilterSettingsDataSource.settingsWereChanged()

        if(settingsWereChanged || sortingMustBeDoneForUpdatedList) {
            if (sortingByDistanceSettingIsActive) {
                sortHotelsByByDistanceFromCenter(hotelsList)
                mFilterSettingsDataSource.setSettingsCurrentState(false)
            } else if (sortingByRoomsSettingIsActive) {
                sortHotelsByAvailableRooms(hotelsList)
                mFilterSettingsDataSource.setSettingsCurrentState(false)
            }
        }
    }

    fun processLaunchingHotelDetailsScreen(hotelId:Int){
        saveChosenHotelId(hotelId)
        navigateToHotelDetailsScreen()
    }

    fun clearCachedData(){
        mFilterSettingsDataSource.clearSettings()
        mHotelsList.clear()
    }
}
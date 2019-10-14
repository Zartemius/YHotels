package com.example.yhotels.presentation.screens.mainscreen

import androidx.fragment.app.Fragment
import com.example.yhotels.data.MainContentDataSource
import com.example.yhotels.presentation.entities.Hotel
import com.example.yhotels.data.entities.HotelDTO
import com.example.yhotels.navigation.Screens
import com.example.yhotels.presentation.BasePresenter
import com.example.yhotels.presentation.entities.FilterSettings
import com.example.yhotels.presentation.mapper.Mapper
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import ru.terrakok.cicerone.Router
import java.io.IOException
import java.lang.NullPointerException
import javax.inject.Inject

class MainScreenPresenter@Inject constructor(router: Router,
                                             mainContentDataSource: MainContentDataSource)
    :BasePresenter<MainScreenContract>() {

    private val mRouter = router
    private val mMainContentDataSource = mainContentDataSource
    private var mHotelsList = ArrayList<Hotel>()
    private var mFilterSettings: FilterSettings = FilterSettings()

    private fun loadHotels(listWasRefreshed:Boolean){
        launch {
                try {
                    val response = getHotelsList()
                    withContext(Dispatchers.Main){
                        if(response.isSuccessful){
                            mHotelsList = Mapper.mapHotelsList(response.body()!!)
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

    private fun sortHotelsByAvailableRooms(hotels:ArrayList<Hotel>){
        hotels.sortByDescending{it.suites?.size}
    }

    private fun sortHotelsByByDistanceFromCenter(hotels:ArrayList<Hotel>){
        hotels.sortBy{ it.distance}
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

        val sortingByDistanceSettingIsActive = mFilterSettings.sortingByDistanceSettingIsActive()
        val sortingByRoomsSettingIsActive = mFilterSettings.sortingByRoomsSettingIsActive()
        val settingsWereChanged = mFilterSettings.settingsWereChanged()

        if (settingsWereChanged || sortingMustBeDoneForUpdatedList) {
            if (sortingByDistanceSettingIsActive) {
                sortHotelsByByDistanceFromCenter(hotelsList)
                mFilterSettings.setSettingsCurrentState(false)
            } else if (sortingByRoomsSettingIsActive) {
                sortHotelsByAvailableRooms(hotelsList)
                    mFilterSettings.setSettingsCurrentState(false)
            }
        }
    }

    fun launchHotelDetailsScreen(hotelId:Int){
        mRouter.navigateTo(Screens.HotelDetailsDestination(hotelId))
    }

    fun launchFilterScreen(fragment: Fragment){
        mRouter.navigateTo(Screens.FilterScreenDestination(fragment,mFilterSettings))
    }

    fun clearAppCachedData(){
        mFilterSettings.clearSettings()
        mHotelsList.clear()
    }

    fun updateFilterSettings(filterSettings: FilterSettings){
        mFilterSettings = filterSettings
    }
}
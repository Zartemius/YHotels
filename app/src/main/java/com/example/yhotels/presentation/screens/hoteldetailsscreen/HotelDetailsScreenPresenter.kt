package com.example.yhotels.presentation.screens.hoteldetailsscreen

import com.example.yhotels.data.HotelInfoDataSource
import com.example.yhotels.data.MainContentDataSource
import com.example.yhotels.presentation.BasePresenter
import com.example.yhotels.presentation.entities.HotelDetails
import com.example.yhotels.presentation.mapper.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.terrakok.cicerone.Router
import java.io.IOException
import javax.inject.Inject

class HotelDetailsScreenPresenter @Inject constructor(router: Router,hotelInfoDataSource: HotelInfoDataSource,
                                                      mainContentDataSource: MainContentDataSource)
                        :BasePresenter<HotelDetailsScreenContract>() {

    private val mHotelInfoDataSource = hotelInfoDataSource
    private val mMainContentDataSource = mainContentDataSource
    private var mHotelDetails:HotelDetails? = null
    private val mRouter = router

    private fun loadScreenContent(){
        if(mHotelDetails == null) {
            launch {
                try{
                        val hotelId = mHotelInfoDataSource.getHotelId()
                        val responseWithContent = mMainContentDataSource.getHotelById(hotelId!!)
                        withContext(Dispatchers.Main) {
                            if (responseWithContent.isSuccessful) {
                                mHotelDetails = Mapper.mapHotelDetails(responseWithContent.body())
                                view?.showProgressBar()
                                view?.loadPicture(mHotelDetails?.imageIsAvailable,mHotelDetails?.imageUrl)
                                try {
                                    view?.loadMainContentInViews(mHotelDetails!!)
                                }catch (e:NullPointerException){
                                    e.printStackTrace()
                                }
                            }
                        }
                }catch(e:NullPointerException){
                  e.printStackTrace()
                } catch (e: HttpException) {
                    e.printStackTrace()
                } catch (e:IOException){
                    view?.showNoInternetWarning()
                    e.printStackTrace()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }else{
            launch {
                withContext(Dispatchers.Main) {
                    view?.loadPicture(mHotelDetails?.imageIsAvailable,mHotelDetails?.imageUrl)
                    try {
                        view?.loadMainContentInViews(mHotelDetails!!)
                        view?.setViewsReadyForDisplaying()
                    }catch (e:NullPointerException){
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun initHotelDetailsScreen(){
        try {
                if (view?.isInternetConnectionActive()!!) {
                    loadScreenContent()
                } else {
                    view?.showNoInternetWarning()
                }
        }catch (e:java.lang.NullPointerException){
            e.printStackTrace()
        }
    }

    override fun takeView(view: HotelDetailsScreenContract) {
        super.takeView(view)
        initHotelDetailsScreen()
    }

    private fun clearCurrentPresenterState(){
        mHotelDetails = null
    }

    fun finishLoading(){
        view?.setViewsReadyForDisplaying()
        view?.hideProgressBar()
    }

    fun navigateToPreviousScreen(){
        clearCurrentPresenterState()
        mRouter.exit()
    }

    fun showHotelOnMap(){
        val lat = mHotelDetails?.lat
        val lon = mHotelDetails?.lon
        if(lat != null && lon != null) {
            view?.callMapApp(lat, lon)
        }
    }
}
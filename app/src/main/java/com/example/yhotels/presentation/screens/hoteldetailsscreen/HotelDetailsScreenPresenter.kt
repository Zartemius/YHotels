package com.example.yhotels.presentation.screens.hoteldetailsscreen

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

class HotelDetailsScreenPresenter @Inject constructor(router: Router,
                                                      mainContentDataSource: MainContentDataSource)
                        :BasePresenter<HotelDetailsScreenContract>() {

    private val mMainContentDataSource = mainContentDataSource
    private var mHotelDetails:HotelDetails? = null
    private val mRouter = router
    private var mHotelId:Int = 0

    private fun loadScreenContent(){
        if(mHotelDetails == null) {
            launch {
                    try{
                        val responseWithContent = mMainContentDataSource.getHotelById(mHotelId)
                        withContext(Dispatchers.Main) {
                            if (responseWithContent.isSuccessful) {
                                mHotelDetails = Mapper.mapHotelDetails(responseWithContent.body())

                                view?.showProgressBar()
                                mHotelDetails?.let{
                                    processLoadingHotelImage(it)
                                    try {
                                        view?.loadMainContentInViews(it)
                                    }catch (e:NullPointerException){
                                        e.printStackTrace()
                                    }
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
                    mHotelDetails?.let {
                        processLoadingHotelImage(it)
                    }
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

    private fun processLoadingHotelImage(hotelDetails: HotelDetails){
        val imageIsAvailable = hotelDetails.imageIsAvailable
        val imageUrl = hotelDetails.imageUrl
        imageUrl?.let {
            view?.loadPicture(imageIsAvailable, imageUrl)
        }
    }

    private fun initHotelDetailsScreen(){
        try {
            if (view?.isInternetConnectionActive()!!) {
                loadScreenContent()
            } else {
                view?.showNoInternetWarning()
            }
        }catch (e:NullPointerException){
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

    fun processReturningToPreviousScreen(){
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

    fun setHotelId(id:Int){
        mHotelId = id
    }
}
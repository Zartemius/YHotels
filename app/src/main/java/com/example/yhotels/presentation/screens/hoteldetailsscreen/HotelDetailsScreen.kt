package com.example.yhotels.presentation.screens.hoteldetailsscreen

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.yhotels.R
import com.example.yhotels.YHotelsApp
import com.example.yhotels.common.isConnectedToNetwork
import com.example.yhotels.presentation.entities.HotelDetails
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.hotel_details_screen_layout.*
import kotlinx.android.synthetic.main.hotel_details_screen_layout.view.*
import kotlinx.android.synthetic.main.progressbar.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception
import java.lang.NullPointerException
import java.util.*
import javax.inject.Inject

class HotelDetailsScreen: Fragment(),
    HotelDetailsScreenContract {

    @Inject
    lateinit var mPresenter: HotelDetailsScreenPresenter
    private lateinit var mTarget:Target

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {

        (activity?.application as YHotelsApp).component.inject(this)
        return inflater.inflate(R.layout.hotel_details_screen_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarLayout.setBackgroundColor(resources.getColor(android.R.color.white))
        progressBar.indeterminateTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))

        toolbar.setTitle(resources.getString(R.string.hotel_details_screen_title))
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24)
        toolbar.setNavigationOnClickListener {
            mPresenter.navigateToPreviousScreen()
        }

        buttonToShowMap.setOnClickListener {
            mPresenter.showHotelOnMap()
        }
    }

    override fun isInternetConnectionActive(): Boolean? {
        return isConnectedToNetwork(activity?.applicationContext)
    }

    override fun loadPicture(imageIsAvailable:Boolean?,pictureUrl:String?) {
        val pxToCutOfBitmap =  1
        if(imageIsAvailable!!) {
            mTarget = object : Target {

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    try {
                        val cropWidth = bitmap?.width!! - pxToCutOfBitmap - pxToCutOfBitmap
                        val cropHeight = bitmap.height - pxToCutOfBitmap - pxToCutOfBitmap
                        val bitmapos = Bitmap.createBitmap(bitmap, pxToCutOfBitmap, pxToCutOfBitmap, cropWidth, cropHeight)
                        view?.hotelImage?.setImageBitmap(bitmapos)
                        mPresenter.finishLoading()
                    }catch (e:NullPointerException){
                        e.printStackTrace()
                    }
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    try {
                        setDefaultPicture(view?.hotelImage!!)
                        mPresenter.finishLoading()
                    }catch (e:NullPointerException){
                        e.printStackTrace()
                    }
                }
            }

            Picasso.get()
                .load(pictureUrl)
                .into(mTarget)
        }else{
            try {
                setDefaultPicture(view?.hotelImage!!)
                mPresenter.finishLoading()
            }catch (e:NullPointerException){
                e.printStackTrace()
            }
        }
    }

    override fun loadMainContentInViews(hotelDetails: HotelDetails) {
        view?.nameText?.text = hotelDetails.name
        view?.addressText?.text = hotelDetails.address
        view?.starsText?.text = hotelDetails.stars
        view?.distanceText?.text = hotelDetails.distance
        view?.suitesText?.text = hotelDetails.suites
    }

    override fun setViewsReadyForDisplaying() {
        view?.addressImage?.visibility = View.VISIBLE
        view?.starsImage?.visibility = View.VISIBLE
        view?.suitesImage?.visibility = View.VISIBLE
        view?.distanceImage?.visibility = View.VISIBLE
        view?.buttonToShowMap?.visibility = View.VISIBLE
    }

    private fun setDefaultPicture(imageView:ImageView){
        val pictureTopMargin = 100
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.layoutParams?.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        imageView.layoutParams?.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
        val params = imageView.layoutParams as ConstraintLayout.LayoutParams
        params.setMargins(0,pictureTopMargin,0,0)
        imageView.layoutParams = params
        imageView.setImageResource(R.drawable.default_hotel_image)
    }

    override fun showNoInternetWarning() {
        Snackbar.make(getView()!!,resources.getString(R.string.no_internet_connection_notification_text),
            Snackbar.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.takeView(this)
    }

    override fun onStop() {
        mPresenter.releaseView()
        super.onStop()
    }

    override fun onBackPressed() {
        mPresenter.navigateToPreviousScreen()
    }

    override fun showProgressBar() {
        progressBarLayout.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBarLayout.visibility = View.GONE
    }

    override fun callMapApp(latitude:Double, longitude:Double){
        val format = "geo:%f,%f"
        val uri = String.format(Locale.ENGLISH,format, latitude, longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context?.startActivity(intent)
    }
}
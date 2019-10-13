package com.example.yhotels.presentation.screens.mainscreen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.yhotels.R
import com.example.yhotels.YHotelsApp
import com.example.yhotels.common.isConnectedToNetwork
import com.example.yhotels.presentation.entities.FilterSettings
import com.example.yhotels.presentation.entities.Hotel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_screen_layout.*
import kotlinx.android.synthetic.main.progressbar.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.NullPointerException
import javax.inject.Inject

class MainScreen:Fragment(), MainScreenContract, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var mPresenter: MainScreenPresenter
    private lateinit var mMainScreenRecyclerViewAdapter: MainScreenRecyclerViewAdapter
    private var mFilterButtonIsEnabled = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity?.application as YHotelsApp).component.inject(this)
        return inflater.inflate(R.layout.main_screen_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = resources.getString(R.string.main_screen_title)

        toolbar.inflateMenu(R.menu.filter_menu)

        toolbar.setOnMenuItemClickListener{it->
            if(mFilterButtonIsEnabled) {
                mPresenter.launchFilterScreen(this)
            }
            true
        }

        mainScreenRecyclerView.layoutManager =
            LinearLayoutManager(activity,RecyclerView.VERTICAL,false)

        mainScreenRecyclerView.setHasFixedSize(true)

        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.takeView(this)
    }

    override fun onStop() {
        mPresenter.releaseView()
        super.onStop()
    }

    override fun showProgressBar() {
        progressBarLayout.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBarLayout.visibility = View.GONE
    }

    override fun hideRefreshingProgressBar() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun isInternetConnectionActive(): Boolean? {
        return isConnectedToNetwork(activity?.applicationContext)
    }

    override fun showNoInternetWarning() {
        try {
                Snackbar.make(
                    getView()!!, resources.getString(R.string.no_internet_connection_notification_text),
                    Snackbar.LENGTH_LONG
                ).show()
        }catch (e:NullPointerException){
            e.printStackTrace()
        }
    }

    override fun setHotelsList(hotels: ArrayList<Hotel>) {
        mMainScreenRecyclerViewAdapter =
            MainScreenRecyclerViewAdapter(hotels,
                { hotel: Hotel -> onHotelsListItemClicked(hotel) })
        mainScreenRecyclerView.adapter = mMainScreenRecyclerViewAdapter
    }

    override fun updateHotelsList(hotels:ArrayList<Hotel>) {
        val hotelDiffUtilCallBack = HotelDiffUtilCallBack(
            mMainScreenRecyclerViewAdapter.getData(),
            hotels
        )
        val hotelDiffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(hotelDiffUtilCallBack)
        mMainScreenRecyclerViewAdapter.setData(hotels)
        hotelDiffResult.dispatchUpdatesTo(mMainScreenRecyclerViewAdapter)
    }

    override fun setFilterButtonIsEnabled() {
        mFilterButtonIsEnabled = true
    }

    override fun onRefresh() {
        mPresenter.applyUpdatesToHotelsList()
    }

    private fun onHotelsListItemClicked(hotel: Hotel){
        try {
            val hotelId = hotel.id
            mPresenter.launchHotelDetailsScreen(hotelId!!)
        }catch (e:NullPointerException){
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        mPresenter.clearAppCachedData()
    }

    override fun setRecyclerViewPreparedForLoading() {
        mainScreenRecyclerView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
    }

    fun handleFilterResult(filterSettings:FilterSettings){
        mPresenter.updateFilterSettings(filterSettings)
    }
}
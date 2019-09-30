package com.example.yhotels.presentation.screens.filterscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yhotels.R
import com.example.yhotels.YHotelsApp
import kotlinx.android.synthetic.main.filter_screen_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class FilterScreen: Fragment(), FilterScreenContract {

    @Inject
    lateinit var mPresenter: FilterScreenPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity?.application as YHotelsApp).component.inject(this)
        return inflater.inflate(R.layout.filter_screen_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setTitle(resources.getString(R.string.filter_screen_title))
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24)
        toolbar.setNavigationOnClickListener {
            mPresenter.navigateToPreviousScreen()
        }

        switchSortingByDistance.setOnCheckedChangeListener { compoundButton, isChecked ->
            processSwitchSortingByDistanceChanged(isChecked)
        }

        switchSortingByRooms.setOnCheckedChangeListener { compoundButton, isChecked ->
            processSwitchSortingByRoomsChanged(isChecked)
        }

        buttonToApplySettings.setOnClickListener {
            mPresenter.applySettings()
        }
    }

    override fun initSwitches(sortingByDistanceDistanceWasChosen: Boolean,
                               sortingByRoomsWasChosen: Boolean) {
        switchSortingByDistance.isChecked = sortingByDistanceDistanceWasChosen
        switchSortingByRooms.isChecked = sortingByRoomsWasChosen
    }

    override fun onStart() {
        super.onStart()
        mPresenter.takeView(this)
    }

    override fun onStop() {
        mPresenter.releaseView()
        super.onStop()
    }


    private fun processSwitchSortingByDistanceChanged(isChecked:Boolean){
        if(isChecked) {
            if(switchSortingByRooms.isChecked){
                switchSortingByRooms.isChecked = false
                mPresenter.setSortingByDistance(isChecked)
                mPresenter.setSortingByRooms(false)
            }else{
                mPresenter.setSortingByDistance(isChecked)
            }
        }else{
            mPresenter.setSortingByDistance(isChecked)
        }
    }

    private fun processSwitchSortingByRoomsChanged(isChecked: Boolean){
        if(isChecked) {
            if(switchSortingByDistance.isChecked){
                switchSortingByDistance.isChecked = false
                mPresenter.setSortingByRooms(isChecked)
                mPresenter.setSortingByDistance(false)
            }else{
                mPresenter.setSortingByRooms(isChecked)
            }
        }else{
            mPresenter.setSortingByRooms(isChecked)
        }
    }

    override fun onBackPressed() {
        mPresenter.navigateToPreviousScreen()
    }
}
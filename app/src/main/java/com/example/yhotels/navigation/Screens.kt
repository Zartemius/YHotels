package com.example.yhotels.navigation

import androidx.fragment.app.Fragment
import com.example.yhotels.presentation.entities.FilterSettings
import com.example.yhotels.presentation.screens.filterscreen.FilterScreen
import com.example.yhotels.presentation.screens.hoteldetailsscreen.HotelDetailsScreen
import com.example.yhotels.presentation.screens.mainscreen.MainScreen
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class MainScreenDestination : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MainScreen()
        }
    }

    class FilterScreenDestination(fragment:Fragment,
                                  private val filterSettings: FilterSettings):SupportAppScreen(){
        private val targetFragment = fragment
        private val requestFilterCode = 11
        override fun getFragment(): Fragment {
            val fragment = FilterScreen.getNewInstance(filterSettings)
            fragment.setTargetFragment(targetFragment,requestFilterCode)
            return fragment
        }
    }

    class HotelDetailsDestination(private val hotelId:Int):SupportAppScreen(){
        override fun getFragment(): Fragment {
            return HotelDetailsScreen.getNewInstance(hotelId)
        }
    }
}
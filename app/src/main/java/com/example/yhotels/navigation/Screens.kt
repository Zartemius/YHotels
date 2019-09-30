package com.example.yhotels.navigation

import androidx.fragment.app.Fragment
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

    class FilterScreenDestination: SupportAppScreen(){
        override fun getFragment(): Fragment {
            return FilterScreen()
        }
    }

    class HotelDetailsDestination:SupportAppScreen(){
        override fun getFragment(): Fragment {
            return HotelDetailsScreen()
        }
    }
}
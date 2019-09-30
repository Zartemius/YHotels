package com.example.yhotels.di

import com.example.yhotels.presentation.MainActivity
import com.example.yhotels.presentation.screens.filterscreen.FilterScreen
import com.example.yhotels.presentation.screens.hoteldetailsscreen.HotelDetailsScreen
import com.example.yhotels.presentation.screens.mainscreen.MainScreen
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    NavigationModule::class,
    DataSourceModule::class,
    PresenterModule::class])

interface AppComponent{
    fun inject(filterScreen: FilterScreen)
    fun inject(hotelDetailsScreen: HotelDetailsScreen)
    fun inject(mainScreen: MainScreen)
    fun inject(mainActivity: MainActivity)
}
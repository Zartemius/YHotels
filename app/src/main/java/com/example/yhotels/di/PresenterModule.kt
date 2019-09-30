package com.example.yhotels.di

import com.example.yhotels.data.FilterSettingsDataSource
import com.example.yhotels.data.HotelInfoDataSource
import com.example.yhotels.data.MainContentDataSource
import com.example.yhotels.presentation.MainActivityPresenter
import com.example.yhotels.presentation.screens.filterscreen.FilterScreenPresenter
import com.example.yhotels.presentation.screens.hoteldetailsscreen.HotelDetailsScreenPresenter
import com.example.yhotels.presentation.screens.mainscreen.MainScreenPresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class PresenterModule {

    @Singleton
    @Provides
    fun provideMainScreenPresenter(router: Router,
                                   filterSettingsDataSource: FilterSettingsDataSource,
                                   mainContentDataSource: MainContentDataSource,
                                   hotelInfoDataSource: HotelInfoDataSource)
            : MainScreenPresenter {
        return MainScreenPresenter(
            router,
            filterSettingsDataSource,
            mainContentDataSource,
            hotelInfoDataSource
        )
    }

    @Singleton
    @Provides
    fun provideFilterScreenPresenter(router: Router,
                                     filterSettingsDataSource: FilterSettingsDataSource)
            : FilterScreenPresenter {
        return FilterScreenPresenter(
            router,
            filterSettingsDataSource
        )
    }

    @Singleton
    @Provides
    fun provideHotelDetailsScreenPresenter(router: Router,
                                           hotelInfoDataSource: HotelInfoDataSource,
                                           mainContentDataSource: MainContentDataSource)
            : HotelDetailsScreenPresenter {
        return HotelDetailsScreenPresenter(
            router,
            hotelInfoDataSource,
            mainContentDataSource
        )
    }

    @Singleton
    @Provides
    fun provideMainActivityPresenter(router:Router):MainActivityPresenter{
        return MainActivityPresenter(router)
    }
}
package com.example.yhotels.di

import com.example.yhotels.data.MainContentDataSource
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
                                   mainContentDataSource: MainContentDataSource)
            : MainScreenPresenter {
        return MainScreenPresenter(router,mainContentDataSource)
    }

    @Singleton
    @Provides
    fun provideFilterScreenPresenter()
            : FilterScreenPresenter {
        return FilterScreenPresenter()
    }

    @Singleton
    @Provides
    fun provideHotelDetailsScreenPresenter(router: Router,
                                           mainContentDataSource:MainContentDataSource)
            : HotelDetailsScreenPresenter {
        return HotelDetailsScreenPresenter(router,mainContentDataSource)
    }
}
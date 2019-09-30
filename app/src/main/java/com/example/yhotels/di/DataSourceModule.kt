package com.example.yhotels.di

import com.example.yhotels.data.FilterSettingsDataSource
import com.example.yhotels.data.HotelInfoDataSource
import com.example.yhotels.data.MainContentDataSource
import com.example.yhotels.data.externaldatasource.RestDataSource
import com.example.yhotels.data.internaldatasource.FilterSettingsStorage
import com.example.yhotels.data.internaldatasource.HotelInfoStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideMainContentDataSource ():MainContentDataSource{
        return RestDataSource()
    }

    @Provides
    @Singleton
    fun provideHotelInfoDataSource():HotelInfoDataSource{
        return HotelInfoStorage()
    }

    @Provides
    @Singleton
    fun provideFilterSettingDataSource():FilterSettingsDataSource{
        return FilterSettingsStorage()
    }
}
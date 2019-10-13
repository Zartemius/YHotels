package com.example.yhotels.di

import com.example.yhotels.data.MainContentDataSource
import com.example.yhotels.data.externaldatasource.RestDataSource
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
}
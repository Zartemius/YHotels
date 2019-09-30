package com.example.yhotels.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class NavigationModule {

    private val navigation:Cicerone<Router>

    init {
        navigation = Cicerone.create()
    }

    @Provides
    @Singleton
    fun provideMainRouter():Router{
        return navigation.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder():NavigatorHolder{
        return navigation.navigatorHolder
    }
}
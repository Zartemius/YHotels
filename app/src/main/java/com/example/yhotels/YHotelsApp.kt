package com.example.yhotels

import android.app.Application
import com.example.yhotels.di.AppComponent
import com.example.yhotels.di.DaggerAppComponent

class YHotelsApp:Application() {

    lateinit var component:AppComponent

    override fun onCreate() {
        super.onCreate()
        component = initDagger()
    }

    private fun initDagger():AppComponent{
        return DaggerAppComponent
            .builder()
            .build()
    }
}
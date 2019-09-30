package com.example.yhotels.common

import android.content.Context
import android.net.ConnectivityManager

fun isConnectedToNetwork(context:Context?):Boolean?{
    val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return cm?.activeNetworkInfo?.isConnectedOrConnecting ?: false
}
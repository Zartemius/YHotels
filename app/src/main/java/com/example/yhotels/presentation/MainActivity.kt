package com.example.yhotels.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.yhotels.R
import com.example.yhotels.YHotelsApp
import com.example.yhotels.navigation.Screens
import com.example.yhotels.presentation.screens.mainscreen.MainScreen
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mRouter:Router

    @Inject
    lateinit var navigatorHolder:NavigatorHolder

    private lateinit var navigator:Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as YHotelsApp).component.inject(this)
        navigator = SupportAppNavigator(this,R.id.main_container)

        if(savedInstanceState == null){
            mRouter.newRootScreen(Screens.MainScreenDestination())
        }
    }

    override fun onResumeFragments() {
        navigatorHolder.setNavigator(navigator)
        super.onResumeFragments()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        processOnBackPressed()
    }

    private fun processOnBackPressed(){
        val fm = supportFragmentManager
        var onBackPressedListener:OnBackPressedListener? = null
        var onBackPressedCalledImMainScreen = false

        for(fragment in fm.fragments){
            if(fragment is OnBackPressedListener){
                onBackPressedListener = fragment
                if(fragment is MainScreen){
                    onBackPressedCalledImMainScreen = true
                }
                break
            }
        }
        if(onBackPressedListener != null){
            if(onBackPressedCalledImMainScreen){
                onBackPressedListener.onBackPressed()
                super.onBackPressed()
            }else{
                onBackPressedListener.onBackPressed()
            }
        }
    }
}

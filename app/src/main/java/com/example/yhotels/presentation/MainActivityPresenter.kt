package com.example.yhotels.presentation

import com.example.yhotels.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(router: Router){

    private val mRouter = router
    private var navigationActionWasPerformed:Boolean = false

    fun bindActivity(){
        if(!navigationActionWasPerformed){
            mRouter.newRootScreen(Screens.MainScreenDestination())
            navigationActionWasPerformed = true
        }
    }

    fun unbindActivity(){
        navigationActionWasPerformed = false
    }
}

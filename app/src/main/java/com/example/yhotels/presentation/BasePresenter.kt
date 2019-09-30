package com.example.yhotels.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<T: BaseView>:CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    protected var view: T? = null

    open fun takeView(view: T) {
        this.view = view
    }

    open fun releaseView() {
        job.cancelChildren()
        this.view = null
    }
}

package com.example.toogoodtothrow

import android.app.Application
import com.example.toogoodtothrow.di.AppContainer
import com.example.toogoodtothrow.di.AppDataContainer

class TooGoodToThrowApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
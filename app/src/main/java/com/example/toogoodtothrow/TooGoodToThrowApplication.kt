package com.example.toogoodtothrow

import android.app.Application
import com.example.toogoodtothrow.data.AppContainer
import com.example.toogoodtothrow.data.AppDataContainer

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
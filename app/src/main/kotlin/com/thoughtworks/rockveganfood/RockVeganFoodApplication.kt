package com.thoughtworks.rockveganfood

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.thoughtworks.rockveganfood.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class RockVeganFoodApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidContext(this@RockVeganFoodApplication)
            logger(AndroidLogger())
            modules(appModule)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}

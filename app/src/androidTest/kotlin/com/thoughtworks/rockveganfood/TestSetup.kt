package com.thoughtworks.rockveganfood

import android.app.Application
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import com.jakewharton.threetenabp.AndroidThreeTen
import com.thoughtworks.rockveganfood.di.appModule
import com.thoughtworks.rockveganfood.di.testModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class RockVeganFoodTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            logger(AndroidLogger())
            modules(appModule + testModule)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}

package com.idocnet.mvvmdemo.app

import android.app.Application
import com.idocnet.mvvmdemo.di.AppComponent
import com.idocnet.mvvmdemo.di.AppModule
import com.idocnet.mvvmdemo.di.DaggerAppComponent

class MyApplication: Application() {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    fun getComponent(): AppComponent = appComponent
}
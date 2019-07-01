package com.setnameinc.newsrss

import android.app.Application
import com.setnameinc.newsrss.di.AppComponent
import com.setnameinc.newsrss.di.CacheModule
import com.setnameinc.newsrss.di.DaggerAppComponent
import com.setnameinc.newsrss.di.RemoteModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .cacheModule(CacheModule(applicationContext))
            .remoteModule(RemoteModule(applicationContext))
            .build()

    }

    companion object{

        lateinit var appComponent: AppComponent

    }

}
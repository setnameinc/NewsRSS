package com.setnameinc.newsrss.di

import com.setnameinc.newsrss.ui.ActivityMain
import com.setnameinc.newsrss.ui.ActivityNewsWebView
import com.setnameinc.newsrss.ui.ActivitySplashScreen
import com.setnameinc.newsrss.ui.FragmentNews
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class, CacheModule::class, RemoteModule::class, DomainModule::class, DataModule::class])
interface AppComponent {


    fun inject(activitySplashScreen: ActivitySplashScreen)

    fun inject(activityMain: ActivityMain)

    fun inject(fragmentNews: FragmentNews)

    fun inject(activityNewsWebView: ActivityNewsWebView)


}
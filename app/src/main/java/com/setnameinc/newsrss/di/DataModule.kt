package com.setnameinc.newsrss.di

import com.setnameinc.newsrss.data.NewsDataProvider
import com.setnameinc.newsrss.data.NewsDataProviderImpl
import com.setnameinc.newsrss.remote.RemoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideNewsDataProvider(remoteDao: RemoteDao): NewsDataProvider = NewsDataProviderImpl(remoteDao)

}
package com.setnameinc.newsrss.di

import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.setnameinc.newsrss.remote.RemoteDao
import com.setnameinc.newsrss.utils.constants.RemoteModuleUtils
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(RemoteModuleUtils.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideGeneralServices(retrofit: Retrofit): RemoteDao = retrofit.create(RemoteDao::class.java)

    /*@Provides
    @Singleton
    fun provideNetworkListener(context: Context):io.reactivex.Observable<Connectivity> =
        ReactiveNetwork.observeNetworkConnectivity(context?.applicationContext)
*/

}
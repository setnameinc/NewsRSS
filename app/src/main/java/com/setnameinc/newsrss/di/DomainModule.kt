package com.setnameinc.newsrss.di

import com.setnameinc.newsrss.utils.constants.Schedulers.JOB
import com.setnameinc.newsrss.utils.constants.Schedulers.UI
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    @Named(JOB)
    fun provideJobScheduler(): Scheduler {
        return Schedulers.computation()
    }

    @Provides
    @Singleton
    @Named(UI)
    fun provideUIScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}
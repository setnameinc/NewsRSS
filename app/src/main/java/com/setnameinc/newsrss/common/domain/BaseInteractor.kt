package com.setnameinc.newsrss.common.domain

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

abstract class BaseInteractor<ResultType, ParameterRemoteType, ParameterCacheType>(
    protected open val jobScheduler: Scheduler,
    protected open val uiScheduler: Scheduler) {

    protected val subscription = CompositeDisposable()

    protected abstract fun buildRemoteObservable(parameter: ParameterRemoteType): Observable<ResultType>
    protected abstract fun buildCacheObservable(parameter: ParameterCacheType): Observable<ResultType>

    open fun unsubscribe(){

        subscription.clear()

    }

}
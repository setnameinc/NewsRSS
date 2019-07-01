package com.setnameinc.newsrss.common.data

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

abstract class Interactor<ResultType, ParameterType>(
    protected open val jobScheduler: Scheduler,
    protected open val uiScheduler: Scheduler
) {

    private val subscription = CompositeDisposable()

    protected abstract fun buildObservable(parameter: ParameterType): Observable<ResultType>

    fun execute(parameter: ParameterType, subscriber: DisposableObserver<ResultType>) {

        subscription.add(buildObservable(parameter)
            .subscribeOn(jobScheduler)
            .observeOn(uiScheduler)
            .subscribeWith(subscriber))

    }

    fun unsubscribe() {
        subscription.clear()
    }

}
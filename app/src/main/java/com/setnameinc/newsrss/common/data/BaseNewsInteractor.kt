package com.setnameinc.newsrss.common.data

import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

abstract class BaseNewsInteractor <ResultType, ParameterRemoteType, ParameterCacheType>(
    protected open val jobScheduler: Scheduler,
    protected open val uiScheduler: Scheduler,
    protected open val networkListener: Observable<Connectivity>
) {

    protected abstract fun onConnected()
    protected abstract fun onDisconnected()

    private val subscription = CompositeDisposable()

    protected abstract fun buildRemoteObservable(parameter: ParameterRemoteType): Observable<ResultType>
    protected abstract fun buildCacheObserable(parameter: ParameterCacheType) : Observable<ResultType>

    fun executeRemoteObservable(parameterRemote: ParameterRemoteType, subscriber: DisposableObserver<ResultType>) {

        val dispose = networkListener.subscribe { connectivity ->
            run {

                when (connectivity.state()) {

                    NetworkInfo.State.CONNECTED -> {

                        onConnected()

                        val dispose = buildRemoteObservable(parameterRemote)
                            .subscribeOn(jobScheduler)
                            .observeOn(uiScheduler)
                            .subscribeWith(subscriber)

                    }

                    NetworkInfo.State.DISCONNECTED -> {

                        onDisconnected()

                    }

                    else -> {

                    }
                }

            }
        }

        subscription.add(dispose)

    }

    fun executeCacheSingle(parameterCache: ParameterCacheType, subscriber: DisposableObserver<ResultType>){

        subscription.add(
            buildCacheObserable(parameterCache)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
                .subscribeWith(subscriber)
        )

    }

    fun unsubscribe() {
        subscription.clear()
    }

}
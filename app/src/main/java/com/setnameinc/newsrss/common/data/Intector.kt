package com.setnameinc.newsrss.common.data

import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

abstract class Interactor<ResultType, ParameterRemoteType, ParameterCacheType>(
    protected open val jobScheduler: Scheduler,
    protected open val uiScheduler: Scheduler,
    protected open val networkListener: Observable<Connectivity>
) {

    private val subscription = CompositeDisposable()

    protected abstract fun buildRemoteObservable(parameter: ParameterRemoteType): Observable<ResultType>
    protected abstract fun buildCacheObservable(parameter: ParameterCacheType): Observable<ResultType>

    fun executeRemote(parameterRemote: ParameterRemoteType, subscriber: DisposableObserver<ResultType>) {

        val dispose = networkListener.subscribe { connectivity ->
            run {

                when (connectivity.state()) {

                    NetworkInfo.State.CONNECTED -> {

                        subscription.add(
                            buildRemoteObservable(parameterRemote)
                                .subscribeOn(jobScheduler)
                                .observeOn(uiScheduler)
                                .subscribeWith(subscriber)
                        )

                    }

                    else -> {

                    }
                }

            }
        }

        subscription.add(dispose)

    }

    fun executeCache(parameterCache:ParameterCacheType, subscriber: DisposableObserver<ResultType>) {

        val dispose = networkListener.subscribe { connectivity ->
            run {

                when (connectivity.state()) {

                    NetworkInfo.State.DISCONNECTED -> {

                        subscription.add(
                            buildCacheObservable(parameterCache)
                                .subscribeOn(jobScheduler)
                                .observeOn(uiScheduler)
                                .subscribeWith(subscriber)
                        )

                    }

                    else -> {

                    }
                }

            }
        }

        subscription.add(dispose)

    }

    fun unsubscribe() {
        subscription.clear()
    }

}
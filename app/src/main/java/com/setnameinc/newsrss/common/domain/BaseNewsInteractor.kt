package com.setnameinc.newsrss.common.domain

import android.net.NetworkInfo
import android.util.Log
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

abstract class BaseNewsInteractor<ResultType, ParameterRemoteType, ParameterCacheType>(
    override val jobScheduler: Scheduler,
    override val uiScheduler: Scheduler,
    protected open val networkListener: Observable<Connectivity>
) : BaseInteractor<ResultType, ParameterRemoteType, ParameterCacheType>(
    jobScheduler = jobScheduler,
    uiScheduler = uiScheduler) {

    private val TAG = BaseNewsInteractor::class.java.simpleName

    private val internetSubscription = CompositeDisposable()

    protected abstract fun onConnected()
    protected abstract fun onDisconnected()

    fun executeRemoteObservable(parameterRemote: ParameterRemoteType, subscriber: DisposableObserver<ResultType>) {

        val dispose = networkListener.subscribe { connectivity ->
            run {

                when (connectivity.state()) {

                    NetworkInfo.State.CONNECTED -> {

                        Log.i(TAG, "connected")

                        onConnected()

                        val dispose = buildRemoteObservable(parameterRemote)
                            .subscribeOn(jobScheduler)
                            .observeOn(uiScheduler)
                            .subscribeWith(subscriber)

                        internetSubscription.add(dispose)

                    }

                    NetworkInfo.State.DISCONNECTED -> {

                        Log.i(TAG, "disconnected")

                        onDisconnected()

                        internetSubscription.clear()

                    }

                    else -> {

                        Log.i(TAG, "state = ${connectivity.state()}")

                    }
                }

            }
        }

        subscription.add(dispose)

    }

    fun executeCacheObservable(parameterCache: ParameterCacheType, subscriber: DisposableObserver<ResultType>) {

        subscription.add(
            buildCacheObservable(parameterCache)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
                .subscribeWith(subscriber)
        )

    }

    override fun unsubscribe() {
        super.unsubscribe()

        internetSubscription.clear()

    }

}
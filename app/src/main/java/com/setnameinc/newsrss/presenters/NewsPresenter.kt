package com.setnameinc.newsrss.presenters

import android.util.Log
import com.setnameinc.newsrss.common.presenters.BaseMainPresenter
import com.setnameinc.newsrss.domain.NewsInteractor
import com.setnameinc.newsrss.entities.ModelOfNews
import com.setnameinc.newsrss.ui.FragmentNewsView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class NewsPresenter @Inject constructor(private val newsInteractor: NewsInteractor) :
    BaseMainPresenter<FragmentNewsView>(), NewsPresenterInterface {

    private val disposableBag: CompositeDisposable = CompositeDisposable()

    private val TAG = this::class.java.simpleName

    private var lastLoaded = 2
    private var lastLoadedFromCache: Pair<Long, Long> = 0L to 8L

    override fun onStart() {

        newsInteractor.executeRemote("${lastLoaded}", object : DisposableObserver<List<ModelOfNews>>() {

            override fun onComplete() {

                lastLoaded++

            }

            override fun onError(e: Throwable) {

                Log.i(TAG, "onError| ${e?.message}")

            }

            override fun onNext(t: List<ModelOfNews>) {

                newsInteractor.insetNews(t)

                Log.i(TAG, "onNext | Remote | list = ${t}")

                /*view*/

            }

        })

        newsInteractor.executeCache(lastLoadedFromCache, object : DisposableObserver<List<ModelOfNews>>() {

            override fun onComplete() {


            }

            override fun onError(e: Throwable) {

                Log.i(TAG, "onError| ${e?.message}")

            }

            override fun onNext(t: List<ModelOfNews>) {

                if (t.size < 10) {

                    //no mote models in DB
                    lastLoadedFromCache = 0L to 0L

                } else {

                    //have data in DB
                    lastLoadedFromCache = lastLoadedFromCache.first + 10 to lastLoadedFromCache.second + 10

                }

                val list = t.distinct()

                Log.i(TAG, "onNext | Cache | onNext = ${list}")

                /*view*/

            }

        })

    }

    override fun onStop() {

        disposableBag.clear()

    }

}

interface NewsPresenterInterface {

}
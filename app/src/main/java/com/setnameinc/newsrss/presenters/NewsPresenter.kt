package com.setnameinc.newsrss.presenters

import android.net.NetworkInfo
import android.util.Log
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.setnameinc.newsrss.common.presenters.BaseMainPresenter
import com.setnameinc.newsrss.common.presenters.BaseMainView
import com.setnameinc.newsrss.domain.NewsInteractor
import com.setnameinc.newsrss.entities.ModelOfNews
import com.setnameinc.newsrss.ui.FragmentNewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsPresenter @Inject constructor(private val newsInteractor: NewsInteractor) :
    BaseMainPresenter<FragmentNewsView>(), NewsPresenterInterface {

    private val TAG = this::class.java.simpleName

    private var lastLoaded = 1

    override fun onStart() {

        newsInteractor.execute("${lastLoaded}", object : DisposableObserver<List<ModelOfNews>>() {

            override fun onComplete() {

                lastLoaded++

            }

            override fun onError(e: Throwable) {

                Log.i(TAG, "onError| ${e?.message}")

            }

            override fun onNext(t: List<ModelOfNews>) {

                Log.i(TAG, "onNext| list = ${t}")

                /*view*/

            }

        })

    }

    override fun onStop() {

    }

}

interface NewsPresenterInterface {

}
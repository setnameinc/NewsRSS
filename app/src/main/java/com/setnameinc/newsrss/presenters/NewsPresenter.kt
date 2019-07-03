package com.setnameinc.newsrss.presenters

import android.util.Log
import com.setnameinc.newsrss.common.presenters.BaseMainPresenter
import com.setnameinc.newsrss.domain.NewsBaseNewsInteractor
import com.setnameinc.newsrss.entities.ModelOfNews
import com.setnameinc.newsrss.ui.FragmentNewsView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class NewsPresenter @Inject constructor(private val newsInteractor: NewsBaseNewsInteractor) :
    BaseMainPresenter<FragmentNewsView>(), NewsPresenterInterface {

    private val disposableBag: CompositeDisposable = CompositeDisposable()

    private val TAG = this::class.java.simpleName

    private var limit: Int = 20

    private var internetAvailable = true

    private val lastLoadedCache = PublishSubject.create<Int>()
    private val lastLoadedRemote = PublishSubject.create<Int>()

    override fun onStart() {

        newsInteractor.initPresenter(this)

        val dispatcher = lastLoadedCache.subscribe {

            Log.i(TAG, "Cache | parameter = ${it}")

            newsInteractor.executeCacheObservable(
                it * limit to (it * limit + limit - 1),
                object : DisposableObserver<List<ModelOfNews>>() {

                    override fun onComplete() {

                    }

                    override fun onNext(list: List<ModelOfNews>) {

                        Log.i(TAG, "Cache | loaded list from DB(${it * limit to (it * limit + limit - 1)})")

                        if (list.isEmpty()) {

                            Log.i(TAG, "Cache | load remote using ${it}")

                            lastLoadedRemote.onNext(it)

                        } else {

                            view.loadList(list)

                        }

                    }

                    override fun onError(e: Throwable) {

                        Log.i(TAG, "onError| ${e.message}")

                    }

                })

        }

        val dispatcher1 = lastLoadedRemote.subscribe {

            Log.i(TAG, "Remote | parameter = ${it + 1}")

            newsInteractor.executeRemoteObservable(
                it + 1,
                object : DisposableObserver<List<ModelOfNews>>() {

                    override fun onComplete() {


                    }

                    override fun onError(e: Throwable) {

                        Log.i(TAG, "onError| ${e.message}")

                    }

                    override fun onNext(list: List<ModelOfNews>) {

                        Log.i(TAG, "Remote | list size = ${list.size}")

                        newsInteractor.insetNews(list)

                    }

                })

        }

        disposableBag.add(dispatcher)
        disposableBag.add(dispatcher1)

    }

    override fun onStop() {

        newsInteractor.removePresenter()

        disposableBag.clear()

    }

    override fun loadFromPosition(pos: Int) {

        if (internetAvailable) {

            Log.i(TAG, "last loaded = $pos")

            this.lastLoadedCache.onNext(pos)

        }

    }

    override fun onConnected() {

        internetAvailable = true

        view.onConnected()

    }

    override fun onDisconnected() {

        internetAvailable = false

        view.onDisconnected()

    }


}

interface NewsPresenterInterface {

    fun loadFromPosition(pos: Int)

    fun onConnected()
    fun onDisconnected()

}
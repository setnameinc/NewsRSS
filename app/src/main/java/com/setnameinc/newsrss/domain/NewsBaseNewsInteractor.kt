package com.setnameinc.newsrss.domain

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.setnameinc.newsrss.common.data.BaseNewsInteractor
import com.setnameinc.newsrss.data.NewsDataProvider
import com.setnameinc.newsrss.entities.ModelOfNews
import com.setnameinc.newsrss.presenters.NewsPresenter
import com.setnameinc.newsrss.presenters.NewsPresenterInterface
import com.setnameinc.newsrss.utils.constants.Schedulers.JOB
import com.setnameinc.newsrss.utils.constants.Schedulers.UI
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class NewsBaseNewsInteractor @Inject constructor(
    @Named(JOB) override val jobScheduler: Scheduler,
    @Named(UI) override val uiScheduler: Scheduler,
    override val networkListener: Observable<Connectivity>,
    val newsDataProvider: NewsDataProvider) : BaseNewsInteractor<List<ModelOfNews>, Int, Pair<Int, Int>>(
    jobScheduler = jobScheduler,
    uiScheduler = uiScheduler,
    networkListener = networkListener
) {

    var presenter:NewsPresenterInterface? = null

    fun initPresenter(presenterInterface: NewsPresenterInterface){
        presenter = presenterInterface
    }

    fun removePresenter(){
        presenter = null
    }

    override fun onConnected() {
        presenter?.onConnected()
    }

   override fun onDisconnected() {
       presenter?.onDisconnected()
    }

    override fun buildRemoteObservable(page: Int): Observable<List<ModelOfNews>> =
        newsDataProvider.getRemoteNewsObservable(page)

    override fun buildCacheObserable(limit: Pair<Int, Int>): Observable<List<ModelOfNews>> =
        newsDataProvider.getCacheNewsSingle(limit)

    fun insetNews(list: List<ModelOfNews>){
        newsDataProvider.insertNews(list)
    }

}
package com.setnameinc.newsrss.domain

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.setnameinc.newsrss.common.data.Interactor
import com.setnameinc.newsrss.data.NewsDataProvider
import com.setnameinc.newsrss.entities.ModelOfNews
import com.setnameinc.newsrss.utils.constants.Schedulers.JOB
import com.setnameinc.newsrss.utils.constants.Schedulers.UI
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class NewsInteractor @Inject constructor(
    @Named(JOB) override val jobScheduler: Scheduler,
    @Named(UI) override val uiScheduler: Scheduler,
    override val networkListener: Observable<Connectivity>,
    val newsDataProvider: NewsDataProvider
) : Interactor<List<ModelOfNews>, String, Pair<Long, Long>>(
    jobScheduler = jobScheduler,
    uiScheduler = uiScheduler,
    networkListener = networkListener
) {

    override fun buildCacheObservable(fromTo: Pair<Long, Long>): Observable<List<ModelOfNews>> =
        newsDataProvider.getCacheNews(from = fromTo.first, to = fromTo.second)

    override fun buildRemoteObservable(page: String): Observable<List<ModelOfNews>> =
        newsDataProvider.getRemoteNews(page)

    fun insetNews(list: List<ModelOfNews>){
        newsDataProvider.insertNews(list)
    }

}
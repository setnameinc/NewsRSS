package com.setnameinc.newsrss.domain

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
    val newsDataProvider: NewsDataProvider
) : Interactor<List<ModelOfNews>, String>(jobScheduler = jobScheduler, uiScheduler = uiScheduler) {

    override fun buildObservable(parameter: String): Observable<List<ModelOfNews>> =
        newsDataProvider.getNews(parameter)

}
package com.setnameinc.newsrss.data

import com.setnameinc.newsrss.entities.ModelOfNews
import com.setnameinc.newsrss.remote.RemoteDao
import io.reactivex.Observable
import javax.inject.Inject

interface NewsDataProvider{
    fun getNews(login:String): Observable<List<ModelOfNews>>
}

class NewsDataProviderImpl @Inject constructor(val remoteDao: RemoteDao) : NewsDataProvider{

    override fun getNews(parameter: String): Observable<List<ModelOfNews>> = remoteDao.getUser(page = parameter).map {
        it.articles
    }

}
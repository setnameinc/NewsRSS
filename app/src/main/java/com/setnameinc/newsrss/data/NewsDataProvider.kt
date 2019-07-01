package com.setnameinc.newsrss.data

import android.util.Log
import com.setnameinc.newsrss.cache.dao.DatabaseDao
import com.setnameinc.newsrss.entities.ModelOfNews
import com.setnameinc.newsrss.entities.cache.ModelDatabaseModelOfNews
import com.setnameinc.newsrss.entities.cache.ModelDatabaseOfNews
import com.setnameinc.newsrss.remote.RemoteDao
import io.reactivex.Observable
import javax.inject.Inject

interface NewsDataProvider {
    fun getRemoteNews(page: String): Observable<List<ModelOfNews>>
    fun getCacheNews(from: Long, to: Long): Observable<List<ModelOfNews>>
    fun insertNews(listOfNews: List<ModelOfNews>)
}

class NewsDataProviderImpl @Inject constructor(val remoteDao: RemoteDao, val databaseDao: DatabaseDao) :
    NewsDataProvider {

    private val TAG = this::class.java.simpleName

    override fun getRemoteNews(page: String): Observable<List<ModelOfNews>> = remoteDao.getUser(page = page).map {
        it.articles
    }

    override fun getCacheNews(from: Long, to: Long): Observable<List<ModelOfNews>> =
        databaseDao.getNewsWithLimit(from, to).map { it ->

            it.map {
                ModelOfNews(
                    it.id,
                    it.modelOfNews.title?: "",
                    it.modelOfNews.urlToImage?: "",
                    it.modelOfNews.description?: "",
                    it.modelOfNews.publishedAt?: "",
                    it.modelOfNews.url?: ""
                )
            }

        }

    override fun insertNews(listOfNews: List<ModelOfNews>) {

        val list1 = listOfNews.map {

            ModelDatabaseModelOfNews(
                it.title ?: "",
                it.urlToImage ?: "",
                it.description ?: "",
                it.publishedAt ?: "",
                it.url ?: ""
            )

        }

        val list2 = databaseDao.getAllNews().map {
            ModelDatabaseModelOfNews(
                it.modelOfNews.title ?: "",
                it.modelOfNews.urlToImage ?: "",
                it.modelOfNews.description ?: "",
                it.modelOfNews.publishedAt ?: "",
                it.modelOfNews.url ?: ""
            )
        }

        var resultList: List<ModelDatabaseModelOfNews>

        if (list2.size > list1.size) {

            resultList = list2.minus(list1)

        } else {

            resultList = list1.minus(list2)

        }

        Log.i(TAG, "this list was input: $resultList")

        resultList.forEach { databaseDao.insertListOfNews(ModelDatabaseOfNews(modelOfNews = it)) }

    }

}
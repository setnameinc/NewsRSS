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
    fun getRemoteNewsObservable(page: Int): Observable<List<ModelOfNews>>
    fun getCacheNewsSingle(pair: Pair<Int, Int>): Observable<List<ModelOfNews>>
    fun insertNews(listOfNews: List<ModelOfNews>)
}

class NewsDataProviderImpl @Inject constructor(val remoteDao: RemoteDao, val databaseDao: DatabaseDao) :
    NewsDataProvider {

    private val TAG = this::class.java.simpleName

    override fun getRemoteNewsObservable(page: Int): Observable<List<ModelOfNews>> =
        remoteDao.getUser(page = page).map {
            it.articles
        }

    override fun insertNews(listOfNews: List<ModelOfNews>) {

        val listFromRemote = listOfNews.map {

            ModelDatabaseModelOfNews(
                it.title ?: "",
                it.urlToImage ?: "",
                it.description ?: "",
                it.publishedAt ?: "",
                it.url ?: ""
            )

        }

        val listFromCache = databaseDao.getAllNews()

        val listForRetain = ArrayList(listFromRemote)

        listForRetain.retainAll(listFromCache)

        Log.i(TAG, "remote list size = ${listFromRemote.size}")

        Log.i(TAG, "cache list size = ${listFromCache.size}")

        val resultList: List<ModelDatabaseModelOfNews>

        when {
            listFromCache.containsAll(listFromRemote) -> {

                //MARK: all lines already in DB

                Log.i(TAG, "duplicated")

                resultList = arrayListOf()

            }
            listForRetain.size == 0 -> {

                //MARK: no common lines

                Log.i(TAG, "insert full remote list, list size = ${listFromRemote.size}")

                resultList = listFromRemote

            }
            else -> resultList = if (listFromCache.size > listFromRemote.size) {

                Log.i(TAG, "insert db list minus remote list")

                listFromCache.minus(listFromRemote)

            } else {

                Log.i(TAG, "insert remote list minus db list")

                listFromRemote.minus(listFromCache)

            }
        }

        var lastIndex = databaseDao.getLastIndex()

        val insetList = mutableListOf<ModelDatabaseOfNews>()

        for (i in resultList) {

            lastIndex++

            insetList.add(ModelDatabaseOfNews(lastIndex, i))

        }

        Log.i(TAG, "db list size = ${listFromCache.size}")

        databaseDao.insertListOfNews(insetList)

        Log.i(TAG, "db list size = ${databaseDao.getAllNews().size}")

    }

    override fun getCacheNewsSingle(pair: Pair<Int, Int>): Observable<List<ModelOfNews>> =
        databaseDao.getNewsWithLimit(pair.first, pair.second).map { it ->

            it.map {
                ModelOfNews(
                    it.id,
                    it.modelOfNews.title ?: "",
                    it.modelOfNews.urlToImage ?: "",
                    it.modelOfNews.description ?: "",
                    it.modelOfNews.publishedAt ?: "",
                    it.modelOfNews.url ?: ""
                )
            }

        }

}
package com.setnameinc.newsrss.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.setnameinc.newsrss.entities.cache.ModelDatabaseModelOfNews
import com.setnameinc.newsrss.entities.cache.ModelDatabaseOfNews
import io.reactivex.Observable

@Dao
interface DatabaseDao {

    @Insert
    fun insertListOfNews(modelOfNews: ModelDatabaseOfNews)

    @Query("SELECT * FROM news")
    fun getAllNews():List<ModelDatabaseOfNews>

    @Query("SELECT * FROM news LIMIT :from, :to")
    fun getNewsWithLimit(from: Long, to: Long): Observable<List<ModelDatabaseOfNews>>

    @Query("DELETE FROM news")
    fun deleteAllNews()

}
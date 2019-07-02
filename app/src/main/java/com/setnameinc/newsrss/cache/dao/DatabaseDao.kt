package com.setnameinc.newsrss.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.setnameinc.newsrss.entities.cache.ModelDatabaseModelOfNews
import com.setnameinc.newsrss.entities.cache.ModelDatabaseOfNews
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfNews(modelOfNews: ModelDatabaseOfNews)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfNews(modelOfNews: List<ModelDatabaseOfNews>)

    @Query("SELECT id FROM news ORDER BY id DESC LIMIT 1")
    fun getLastIndex():Long

    @Query("SELECT modelOfNews FROM news")
    fun getAllNews():List<ModelDatabaseModelOfNews>

    @Query("SELECT * FROM news LIMIT :from, :to")
    fun getNewsWithLimit(from:Int, to:Int): Observable<List<ModelDatabaseOfNews>>

    @Query("DELETE FROM news")
    fun deleteAllNews()

}
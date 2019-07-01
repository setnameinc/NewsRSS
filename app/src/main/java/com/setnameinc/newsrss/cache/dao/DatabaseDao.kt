package com.setnameinc.newsrss.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.setnameinc.newsrss.entities.cache.ModelDatabaseOfNews

@Dao
interface DatabaseDao {

    /*@Insert
    fun insertListOfNews(listOfNews: List<ModelDatabaseOfNews>)

    @Query("SELECT * FROM news LIMIT :from, :to")
    fun getNewsWithLimit(from:Int, to:Int): List<ModelDatabaseOfNews>

    @Query("DELETE FROM news")
    fun deleteAllNews(id: Long)*/

}
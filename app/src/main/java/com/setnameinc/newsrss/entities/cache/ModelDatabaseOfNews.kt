package com.setnameinc.newsrss.entities.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.setnameinc.newsrss.entities.ModelOfNews

@Entity(tableName = "news")
data class ModelDatabaseOfNews(
    @PrimaryKey
    val id: Long,
    @ColumnInfo
    val modelOfNews: ModelDatabaseModelOfNews)

data class ModelDatabaseModelOfNews(
    val title: String,
    val urlToImage: String,
    val description: String,
    val publishedAt: String,
    val url: String
)

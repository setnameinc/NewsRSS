package com.setnameinc.newsrss.entities

data class ModelOfNews(val id:Long,
                       val title: String,
                       val urlToImage: String,
                       val description: String,
                       val publishedAt: String,
                       val url: String)
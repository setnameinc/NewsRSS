package com.setnameinc.newsrss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.setnameinc.newsrss.entities.ListNews

class NewsViewModel() : ViewModel(){

    var allNews:ArrayList<ListNews> = arrayListOf()

    var position = 0

    fun addToAllNews(listNews: List<ListNews>){
        allNews.addAll(listNews)
    }

}
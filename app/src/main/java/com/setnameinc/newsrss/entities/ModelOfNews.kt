package com.setnameinc.newsrss.entities

data class ModelOfNews(val id:Long,
                       val title: String,
                       val urlToImage: String,
                       val description: String,
                       val publishedAt: String,
                       val url: String) : ListNews() {

    override fun getListItemType(): Int = ListNewsType.POST.type

}

abstract class ListNews{

    enum class ListNewsType(val type: Int) {

        POST(0),
        UPDATE(1);

    }

    abstract fun getListItemType(): Int

}
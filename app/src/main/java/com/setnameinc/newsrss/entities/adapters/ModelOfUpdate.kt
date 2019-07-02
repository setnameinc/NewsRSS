package com.setnameinc.newsrss.entities.adapters

import com.setnameinc.newsrss.entities.ListNews

data class ModelOfUpdate(val message:String) : ListNews(){

    override fun getListItemType(): Int = ListNewsType.UPDATE.type

}
package com.setnameinc.newsrss.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.setnameinc.newsrss.App
import com.setnameinc.newsrss.R
import com.setnameinc.newsrss.common.presenters.BasePresenter
import com.setnameinc.newsrss.common.ui.fragments.BaseFragment
import com.setnameinc.newsrss.utils.annotations.Layout

class ActivityNewsWebView : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_news_web_view)
    }


}
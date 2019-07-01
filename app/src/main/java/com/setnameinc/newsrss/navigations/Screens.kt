package com.setnameinc.newsrss.navigations

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.setnameinc.newsrss.ui.ActivityMain
import com.setnameinc.newsrss.ui.ActivityNewsWebView
import com.setnameinc.newsrss.ui.FragmentNews
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Fragments (private val simpleName: String?) : SupportAppScreen() {

    override fun getFragment(): Fragment = when (simpleName) {
        FragmentNews::class.java.simpleName -> FragmentNews()
        else -> Fragment()
    }

}

class Activities (private val simpleName: String?) : SupportAppScreen() {

    override fun getActivityIntent(context: Context?): Intent = when (simpleName) {
        ActivityNewsWebView::class.java.simpleName -> Intent(context, ActivityNewsWebView::class.java)
        else -> super.getActivityIntent(context)
    }

}
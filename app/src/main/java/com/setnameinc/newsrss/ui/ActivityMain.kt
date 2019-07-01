package com.setnameinc.newsrss.ui

import android.os.Bundle
import com.setnameinc.newsrss.App
import com.setnameinc.newsrss.R
import com.setnameinc.newsrss.common.ui.activities.BaseMainActivity
import com.setnameinc.newsrss.navigations.Fragments
import com.setnameinc.newsrss.utils.annotations.Layout
import com.setnameinc.newsrss.utils.annotations.MainContainer

@Layout(R.layout.activity_main)
@MainContainer(R.id.activity_main__main_container)
class ActivityMain : BaseMainActivity(), ActivityMainInterface {

    override fun inject() {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openFragmentNews()

    }

    override fun openFragmentNews() {
        navigateTo(FragmentNews::class.java.simpleName)
    }

}

interface ActivityMainInterface{
    fun openFragmentNews()
}

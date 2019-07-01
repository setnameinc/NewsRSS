package com.setnameinc.newsrss.ui

import com.setnameinc.newsrss.App
import com.setnameinc.newsrss.R
import com.setnameinc.newsrss.common.presenters.BaseMainView
import com.setnameinc.newsrss.common.presenters.BasePresenter
import com.setnameinc.newsrss.common.ui.fragments.BaseFragment
import com.setnameinc.newsrss.presenters.NewsPresenter
import com.setnameinc.newsrss.utils.annotations.Layout
import javax.inject.Inject

@Layout(R.layout.fragment_news)
class FragmentNews : BaseFragment(), FragmentNewsView{

    override fun showError(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Inject
    lateinit var newsPresenter: NewsPresenter

    override fun getPresenter(): BasePresenter<*, *> = newsPresenter

    override fun inject() {
        App.appComponent.inject(this)
    }


}

interface FragmentNewsView : BaseMainView {

}
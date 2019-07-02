package com.setnameinc.newsrss.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.setnameinc.newsrss.App
import com.setnameinc.newsrss.R
import com.setnameinc.newsrss.adapters.NewsAdapter
import com.setnameinc.newsrss.adapters.NewsAdapterClickListener
import com.setnameinc.newsrss.common.presenters.BaseMainView
import com.setnameinc.newsrss.common.presenters.BasePresenter
import com.setnameinc.newsrss.common.ui.fragments.BaseFragment
import com.setnameinc.newsrss.entities.ListNews
import com.setnameinc.newsrss.entities.adapters.ModelOfUpdate
import com.setnameinc.newsrss.presenters.NewsPresenter
import com.setnameinc.newsrss.utils.annotations.Layout
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

@Layout(R.layout.fragment_news)
class FragmentNews : BaseFragment(), FragmentNewsView, NewsAdapterClickListener, OnBottomReachedListener {

    private val TAG = this::class.java.simpleName

    var listOfNews = arrayListOf<ListNews>()

    @Inject
    lateinit var newsPresenter: NewsPresenter

    override fun getPresenter(): BasePresenter<*, *> = newsPresenter

    override fun inject() {
        App.appComponent.inject(this)
    }

    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter(listOfNews, this, this)

        fragment_news__rv.apply {

            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this.context.applicationContext)

        }

    }

    override fun onStart() {
        super.onStart()

        newsPresenter.loadFrom(0 / 10)

    }

    override fun showError(string: String) {
        //
    }

    override fun loadList(listNews: List<ListNews>) {
        //some bug
        if (listOfNews.size > listNews.size) {
            listOfNews.addAll(listOfNews.minus(listNews))
        } else {
            listOfNews.addAll(listNews.minus(listOfNews))
        }
        newsAdapter.notifyDataSetChanged()
    }

    override fun onPostClickListener(url: String) {
        Log.i(TAG, "onClick: url = ${url}")
    }

    override fun onBottomReached(position: Int) {
        newsPresenter.loadFrom(position / 10)
    }

    override fun onDisconnected() {
        listOfNews.add(ModelOfUpdate(""))
        newsAdapter.notifyItemInserted(listOfNews.lastIndex)
    }

    override fun onConnected() {
        listOfNews.removeAt(listOfNews.lastIndex)
        newsAdapter.notifyItemRemoved(listOfNews.lastIndex)
    }


}

interface FragmentNewsView : BaseMainView {

    fun loadList(listNews: List<ListNews>)

    fun onDisconnected()
    fun onConnected()

}

interface OnBottomReachedListener {

    fun onBottomReached(position: Int)

}
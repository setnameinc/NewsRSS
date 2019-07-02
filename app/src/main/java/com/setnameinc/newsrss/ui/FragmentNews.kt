package com.setnameinc.newsrss.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
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
import com.setnameinc.newsrss.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

@Layout(R.layout.fragment_news)
class FragmentNews : BaseFragment(), FragmentNewsView, NewsAdapterClickListener, OnBottomReachedListener {

    private val TAG = this::class.java.simpleName

    override fun getPresenter(): BasePresenter<*, *> = newsPresenter

    override fun inject() {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var newsPresenter: NewsPresenter

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var newsViewModel: NewsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (newsViewModel == null) {
            newsViewModel = ViewModelProviders.of(activity!!).get(NewsViewModel::class.java)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated | ")

        newsAdapter = NewsAdapter(newsViewModel!!.allNews, this, this)
        linearLayoutManager = LinearLayoutManager(this.context?.applicationContext)

        fragment_news__rv.apply {

            adapter = newsAdapter
            layoutManager = linearLayoutManager

        }

    }

    override fun onStart() {
        super.onStart()

        if (newsViewModel!!.allNews.size == 0) {

            Log.i(TAG, "load list")

            newsPresenter.loadFrom(0 / 10)

        } else if ( (newsViewModel!!.position != 0) ) {

            fragment_news__rv.scrollToPosition(newsViewModel!!.position)

            Log.i(TAG, "onStart | pos = ${newsViewModel!!.position}")

        }

    }

    override fun onStop() {
        super.onStop()

        if (newsViewModel!!.position == 0) {

            newsViewModel!!.position = linearLayoutManager.findFirstVisibleItemPosition()

        }

    }

    override fun loadList(listNews: List<ListNews>) {
        //some bugs :(
        val listResult = arrayListOf<ListNews>()
        if (newsViewModel!!.allNews.size > listNews.size) {
            listResult.addAll(newsViewModel!!.allNews.minus(listNews))
        } else {
            listResult.addAll(listNews.minus(newsViewModel!!.allNews))
        }

        newsViewModel!!.addToAllNews(listResult)

        newsAdapter.notifyDataSetChanged()
    }

    override fun onPostClickListener(url: String) {
        Log.i(TAG, "onClick: url = ${url}")
    }

    override fun onBottomReached(position: Int) {

        Log.i(TAG, "Bottom reached")

        newsPresenter.loadFrom(position / 10)

    }

    override fun onDisconnected() {
        Log.i(TAG, "onDisconnected")
        newsViewModel!!.allNews.add(ModelOfUpdate(""))
        newsAdapter.notifyItemInserted(newsViewModel!!.allNews.lastIndex)
    }

    override fun onConnected() {
        Log.i(TAG, "onConnected")
        newsViewModel!!.allNews.remove(ModelOfUpdate(""))
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
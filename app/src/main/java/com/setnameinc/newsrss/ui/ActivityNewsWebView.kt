package com.setnameinc.newsrss.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.setnameinc.newsrss.App
import com.setnameinc.newsrss.R
import com.setnameinc.newsrss.common.ui.activities.BaseNewsWebView
import com.setnameinc.newsrss.utils.annotations.Layout
import com.setnameinc.newsrss.utils.constants.PassKeys
import kotlinx.android.synthetic.main.activity_news_web_view.*

@Layout(R.layout.activity_news_web_view)
class ActivityNewsWebView : BaseNewsWebView() {

    private val TAG = this::class.java.simpleName

    override fun inject() {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra(PassKeys.URL_KEY)

        activity_news_web_view__wv.loadUrl(url)

        activity_news_web_view__wv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                activity_news_web_view__pb.visibility = View.GONE
            }
        }

/*
        Log.i(TAG, "onCreate | url = $url")
*/

    }

}
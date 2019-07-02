package com.setnameinc.newsrss.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.setnameinc.newsrss.R
import com.setnameinc.newsrss.entities.ListNews
import com.setnameinc.newsrss.entities.ModelOfNews
import com.setnameinc.newsrss.ui.OnBottomReachedListener

class NewsAdapter(
    private val items: ArrayList<ListNews>,
    private val newsAdapterClickListener: NewsAdapterClickListener,
    private val onBottomReachedListener: OnBottomReachedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName

    private var currentPos = 0

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        val viewViewHolder: View
        return when (getItemViewType(position)) {

            ListNews.ListNewsType.POST.type -> {
                viewViewHolder =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_news_post, parent, false)

                ViewHolderPost(viewViewHolder, newsAdapterClickListener)

            }

            ListNews.ListNewsType.UPDATE.type -> {
                viewViewHolder =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_news_update, parent, false)

                ViewHolderUpdate(viewViewHolder, newsAdapterClickListener)

            }

            else -> null!!//never happened

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (items.size > 0) {

            if (position == items.lastIndex) {
                onBottomReachedListener.onBottomReached(position)
            }

        }

        val listWelcome: ListNews = items[position]
        when (getItemViewType(position)) {

            ListNews.ListNewsType.POST.type -> {

                (holder as ViewHolderPost).bindType(
                    listWelcome
                )

            }

            ListNews.ListNewsType.UPDATE.type -> {

                (holder as ViewHolderUpdate).bindType(
                    listWelcome
                )
                
            }

        }
    }

    inner class ViewHolderPost(private val mView: View, private val adapterClickListener: NewsAdapterClickListener) :
        ViewHolder(mView), View.OnClickListener {

        init {

            mView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            if (currentPos != adapterPosition) {

                currentPos = adapterPosition

                notifyItemChanged(adapterPosition)

            }
        }

        override fun bindType(listWelcome: ListNews) {

            val modelOfNews = listWelcome as ModelOfNews

            setPost(modelOfNews)

            if (adapterPosition == currentPos) {

                setClickListener(modelOfNews.url)

            }


        }

        private fun setPost(modelOfNews: ModelOfNews) {

            Glide.with(mView).load(modelOfNews.urlToImage)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .into(mView.findViewById<ImageView>(R.id.item_news_post__iv_image))

            mView.findViewById<TextView>(R.id.item_news_post__tv_title).text = modelOfNews.title

            mView.findViewById<TextView>(R.id.item_news_post__tv_date).text = getDate(modelOfNews.publishedAt)

            mView.findViewById<TextView>(R.id.item_news_post__tv_desc).text = modelOfNews.description

        }

        private fun setClickListener(url: String) {
            adapterClickListener.onPostClickListener(url)
        }

        private fun getDate(strDate: String): String {
            return "2h ago"
        }


    }

    private class ViewHolderUpdate(
        private val mView: View,
        private val adapterClickListener: NewsAdapterClickListener
    ) : ViewHolder(mView) {

        override fun bindType(listWelcome: ListNews) {


        }


    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun bindType(listWelcome: ListNews)

    }

    override fun getItemViewType(position: Int): Int  = items[position].getListItemType()


    override fun getItemCount(): Int = items.size

}

interface NewsAdapterClickListener {

    fun onPostClickListener(url: String)

}

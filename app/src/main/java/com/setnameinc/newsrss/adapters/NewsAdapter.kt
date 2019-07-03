package com.setnameinc.newsrss.adapters

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
import com.setnameinc.newsrss.entities.adapters.ModelOfUpdate
import com.setnameinc.newsrss.ui.OnBottomReachedListener

class NewsAdapter(
    private val items: ArrayList<ListNews>,
    private val newsAdapterClickListener: NewsAdapterClickListener,
    private val onBottomReachedListener: OnBottomReachedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName

    private var currentPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder<*> {

        val viewViewHolder: View
        return when (type) {

            ListNews.ListNewsType.POST.type -> {

                /*Log.i(TAG, "OnCreateViewHolder | post type, type = $type")*/

                viewViewHolder =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_news_post, parent, false)

                ViewHolderPost(viewViewHolder, newsAdapterClickListener)

            }

            ListNews.ListNewsType.UPDATE.type -> {

                /*Log.i(TAG, "OnCreateViewHolder | update type, type = $type")*/

                viewViewHolder =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_news_update, parent, false)

                ViewHolderUpdate(viewViewHolder, newsAdapterClickListener)

            }

            else -> null!!//never happened

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val listWelcome: ListNews = items[position]

        /*Log.i(TAG,
            "onBindViewHolder | list size = ${items.size}, pos = $position, type = ${listWelcome.getListItemType()}"
        )*/

        when (holder) {
            is ViewHolderPost -> holder.bindType(
                listWelcome as ModelOfNews
            )
            is ViewHolderUpdate -> holder.bindType(
                listWelcome as ModelOfUpdate
            )
        }

        if (items.size > 0) {

            if (position == items.lastIndex) {
                onBottomReachedListener.onBottomReached(position)
            }

        }

    }

    inner class ViewHolderPost(private val mView: View, private val adapterClickListener: NewsAdapterClickListener) :
        ViewHolder<ModelOfNews>(mView), View.OnClickListener {

        init {

            mView.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {

            if (currentPosition != adapterPosition) {

                currentPosition = adapterPosition

                notifyItemChanged(adapterPosition)

            }

        }

        override fun bindType(listWelcome: ModelOfNews) {

            setPost(listWelcome)

            if (adapterPosition == currentPosition) {

                setClickListener(listWelcome.url)

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
    ) : ViewHolder<ModelOfUpdate>(mView) {

        override fun bindType(listWelcome: ModelOfUpdate) {

            setView()

        }

        fun setView() {

        }


    }

    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun bindType(listWelcome: T)

    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {

        is ModelOfNews -> {
            ListNews.ListNewsType.POST.type
        }
        is ModelOfUpdate -> {
            ListNews.ListNewsType.UPDATE.type
        }
        else -> throw IllegalArgumentException("Invalid type of data " + position)

    }


    override fun getItemCount(): Int = items.size

}

interface NewsAdapterClickListener {

    fun onPostClickListener(url: String)

}

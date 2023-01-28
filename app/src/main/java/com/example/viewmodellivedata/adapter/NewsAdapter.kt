package com.example.viewmodellivedata.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.viewmodellivedata.R
import com.example.viewmodellivedata.model.Article

class NewsAdapter(
    private val allNewsList: MutableList<Article?>,
    private val onLinkClicked: (Article) -> Unit,
    private val onSaveOrRemove: (Article) -> Unit,
) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_custom_layout, parent, false)
        return ViewHolder(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsPosition = allNewsList[position]
//        holder.itemView.apply{
//            startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_in))
//        }
        with(holder) {
            uiTvTitle.text = itemsPosition?.title
            uiTvDescription.text = itemsPosition?.description
            uiIvNewsImage.let {
                Glide.with(it)
                    .load(itemsPosition?.urlToImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.uiImageProgress.visibility = View.VISIBLE
                            return false
                        }
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.uiImageProgress.visibility = View.GONE
                            return false
                        }
                    })
                    .into(holder.uiIvNewsImage)
            }
            uiBtWebLink.apply {
                this.setOnClickListener {
                    allNewsList[position]?.let { it1 -> onLinkClicked(it1) }
                }
            }
            uiIvSaveOrRemove
        }
        if (itemsPosition?.isSaved == true) {
            holder.uiIvSaveOrRemove.setImageResource(R.drawable.ic_baseline_bookmarked)
        } else {
            holder.uiIvSaveOrRemove.setImageResource(R.drawable.ic_baseline_not_bookmarked)
        }
    }

    override fun getItemCount(): Int {
        return allNewsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uiTvTitle: TextView = itemView.findViewById(R.id.uiTvTitle)
        val uiIvNewsImage: ImageView = itemView.findViewById(R.id.uiIvNewsImage)
        val uiTvDescription: TextView = itemView.findViewById(R.id.uiTvDescription)
        val uiImageProgress: ProgressBar = itemView.findViewById(R.id.uiImageProgress)
        val uiBtWebLink: Button = itemView.findViewById(R.id.uiBtWebLink)
        val uiIvSaveOrRemove: ImageView = itemView.findViewById(R.id.uiIvSaveOrRemove)

        init {
            uiIvSaveOrRemove.setOnClickListener {
                allNewsList[adapterPosition]?.let { it1 -> onSaveOrRemove(it1) }
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onNewsChanged(newsList: List<Article>?) {
        allNewsList.clear()
        if (newsList != null) {
            allNewsList.addAll(newsList)
        }
        notifyDataSetChanged()
    }
}
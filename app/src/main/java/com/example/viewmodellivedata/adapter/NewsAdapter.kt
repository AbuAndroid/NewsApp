package com.example.viewmodellivedata.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.viewmodellivedata.R
import com.example.viewmodellivedata.model.Article

class NewsAdapter(
    private val allNewsList: MutableList<Article>,
    private val onLinkClicked: (Article) -> Unit,
    private val onSave: (Article) -> Unit,
    private val onRemove: (Article) -> Unit
    ) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_custom_layout,parent,false)
        return ViewHolder(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsPosition = allNewsList[position]
        with(holder){
            uiTvTitle.text = itemsPosition.title
            uiTvDescription.text = itemsPosition.description
            uiIvNewsImage.let {
                Glide.with(it)
                    .load(itemsPosition.urlToImage)
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
            uiTvWebLink.apply {
                text = itemsPosition.url
                this.setOnClickListener {
                    onLinkClicked(allNewsList[position])
                }
            }
            uiIvSave.setOnClickListener {
                Log.e("save","save item clicked..  "+allNewsList[adapterPosition])
                onSave(allNewsList[adapterPosition])
                notifyDataSetChanged()
            }
            uiIvRemove.setOnClickListener {
                Log.e("remove","remove item clicked.."+allNewsList[adapterPosition])
                onRemove(allNewsList[adapterPosition])
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return allNewsList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val uiTvTitle:TextView = itemView.findViewById(R.id.uiTvTitle)
        val uiIvNewsImage:ImageView = itemView.findViewById(R.id.uiIvNewsImage)
        val uiTvDescription:TextView = itemView.findViewById(R.id.uiTvDescription)
        val uiImageProgress:ProgressBar = itemView.findViewById(R.id.uiImageProgress)
        val uiTvWebLink:TextView = itemView.findViewById(R.id.uiTvWebLink)
        val uiIvSave:ImageView = itemView.findViewById(R.id.uiIvSave)
        val uiIvRemove:ImageView = itemView.findViewById(R.id.uiIvRemove)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun onNewsChanged(newsList: List<Article>) {
        allNewsList.clear()
        allNewsList.addAll(newsList)
        notifyDataSetChanged()
    }
}
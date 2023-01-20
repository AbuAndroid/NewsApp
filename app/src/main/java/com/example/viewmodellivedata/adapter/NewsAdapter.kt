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
    private val onSaveOrDeleteItem: (Article) -> Unit
    ) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_custom_layout,parent,false)
        return ViewHolder(layoutInflater)
    }

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
            uiIvSave.apply {
                this.setOnClickListener {
//                    if(itemsPosition.isSaved==true){
//                        holder.uiIvSave.setImageResource(R.drawable.ic_baseline_bookmark_added)
//                    }else{
//                        holder.uiIvSave.setImageResource(R.drawable.ic_baseline_bookmark_add)
//                    }
                    onSaveOrDeleteItem(allNewsList[position])
                }
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

//        if(allNewsList[position].isSaved == true){
//            allNewsList[position].isSaved = false
//            this.setImageResource(R.drawable.ic_baseline_bookmark_add)
//
//        }else{
//            allNewsList[position].isSaved = true
//            this.setImageResource(R.drawable.ic_baseline_bookmark_added)
//        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun onNewsChanged(newsList: List<Article>) {
        allNewsList.clear()
        allNewsList.addAll(newsList)
        notifyDataSetChanged()
    }
}
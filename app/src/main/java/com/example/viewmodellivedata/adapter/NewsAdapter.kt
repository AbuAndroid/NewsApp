package com.example.viewmodellivedata.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewmodellivedata.databinding.NewsCustomLayoutBinding
import com.example.viewmodellivedata.model.Article

class NewsAdapter(
    private val allNewsList: MutableList<Article>,
    private val onItemClick: (Article) -> Unit
    ) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsCustomLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsPosition = allNewsList[position]
        Glide.with(holder.itemView)
            .load(itemsPosition.urlToImage)
            .into(holder.binding.uiIvNewsImage)
        holder.binding.uiTvTitle.text = allNewsList[position].title
        holder.binding.uiTvDescription.text = allNewsList[position].description
    }

    override fun getItemCount(): Int {
        return allNewsList.size
    }

    class ViewHolder(val binding: NewsCustomLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                //onItemClick(allNewsList[adapterPosition])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onNewsChanged(newsList: List<Article>) {
        this.allNewsList.clear()
        this.allNewsList.addAll(newsList)
        notifyDataSetChanged()
    }
}
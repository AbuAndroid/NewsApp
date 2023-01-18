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
    //private val onItemClick: (Article) -> Unit
    ) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsCustomLayoutBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsPosition = allNewsList[position]
        Glide.with(holder.itemView)
            .load(itemsPosition.urlToImage)
            .into(holder.binding.uiIvNewsImage)
        holder.binding.uiTvTitle.text = itemsPosition.title
        holder.binding.uiTvDescription.text = itemsPosition.description
    }

    override fun getItemCount(): Int {
        return allNewsList.size
    }

    class ViewHolder(val binding: NewsCustomLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    @SuppressLint("NotifyDataSetChanged")
    fun onNewsChanged(newsList: List<Article>) {
        allNewsList.clear()
        allNewsList.addAll(newsList)
        notifyDataSetChanged()
    }
}
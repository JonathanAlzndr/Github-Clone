package com.jonathan.mygithubsearchuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonathan.mygithubsearchuser.data.response.ItemsItem
import com.jonathan.mygithubsearchuser.databinding.ItemReviewBinding
import com.jonathan.mygithubsearchuser.ui.detail.DetailActivity

class ListGithubAdapter : ListAdapter<ItemsItem, ListGithubAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, review.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ItemsItem){
            binding.username.text = review.login
            Glide.with(binding.root.context)
                .load(review.avatarUrl)
                .into(binding.imageView)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
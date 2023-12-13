package com.jonathan.mygithubsearchuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonathan.mygithubsearchuser.R
import com.jonathan.mygithubsearchuser.data.database.FavoriteUser
import com.jonathan.mygithubsearchuser.ui.detail.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FViewHolder>() {

    private var userList = emptyList<FavoriteUser>()

    class FViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.usernameFav)
        val img: ImageView = itemView.findViewById(R.id.imageViewFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: FViewHolder, position: Int) {
        val item = userList[position]
        holder.name.text = item.username
        Glide.with(holder.img.context)
            .load(item.avatarUrl)
            .into(holder.img)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, item.username)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setData(userList: List<FavoriteUser>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}
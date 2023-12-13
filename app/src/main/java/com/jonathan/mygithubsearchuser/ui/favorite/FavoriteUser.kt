package com.jonathan.mygithubsearchuser.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathan.mygithubsearchuser.databinding.ActivityFavoriteUserBinding
import com.jonathan.mygithubsearchuser.ui.FavoriteAdapter
import com.jonathan.mygithubsearchuser.ui.helper.ViewModelFactory

class FavoriteUser : AppCompatActivity() {

    private val favoriteViewModel by viewModels<FavoriteUserViewModel>(){
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        binding.rvDetailFavorite.adapter = adapter
        binding.rvDetailFavorite.layoutManager = LinearLayoutManager(this)

        favoriteViewModel.readAllData.observe(this)  { favoriteUser ->
            adapter.setData(favoriteUser)
        }
    }

}
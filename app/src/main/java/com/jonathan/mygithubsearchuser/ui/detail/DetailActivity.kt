package com.jonathan.mygithubsearchuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jonathan.mygithubsearchuser.R
import com.jonathan.mygithubsearchuser.data.database.FavoriteUser
import com.jonathan.mygithubsearchuser.databinding.ActivityDetailBinding
import com.jonathan.mygithubsearchuser.ui.helper.ViewModelFactory


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }
    private var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        val btnFavorite = binding.fabFav

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        username.let {
            detailViewModel.showUserDetail(it)
            sectionsPagerAdapter.username = it
        }

        detailViewModel.detailUserResponse.observe(this) {
            binding.username.text = it.login
            binding.name.text = it.name
            binding.followers.text = resources.getString(R.string.followers, it.followers)
            binding.following.text = resources.getString(R.string.following, it.following)
            Glide.with(binding.root.context)
                .load(it.avatarUrl)
                .into(binding.profileImage)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.getFavoriteUserByUsername(username).observe(this) {
            if(it == null) {
                binding.fabFav.setImageResource(R.drawable.ic_favorite_border)
                isFavorite = false
            } else {
                binding.fabFav.setImageResource(R.drawable.ic_favorite_filled)
                isFavorite = true
            }
        }

        btnFavorite.setOnClickListener {
           if(isFavorite) {
               detailViewModel.delete(FavoriteUser(username))
           } else {
               val newFavUser = detailViewModel.getUsernameAvatar()
               detailViewModel.insert(newFavUser)
           }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) binding.progressBar2.visibility = View.VISIBLE
        else binding.progressBar2.visibility = View.INVISIBLE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}
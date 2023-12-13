package com.jonathan.mygithubsearchuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathan.mygithubsearchuser.R
import com.jonathan.mygithubsearchuser.data.response.ItemsItem
import com.jonathan.mygithubsearchuser.databinding.ActivityMainBinding
import com.jonathan.mygithubsearchuser.ui.ListGithubAdapter
import com.jonathan.mygithubsearchuser.ui.ThemeSetting.SettingPreferences
import com.jonathan.mygithubsearchuser.ui.ThemeSetting.ThemeSettingActivity
import com.jonathan.mygithubsearchuser.ui.ThemeSetting.ThemeViewModel
import com.jonathan.mygithubsearchuser.ui.ThemeSetting.dataStore
import com.jonathan.mygithubsearchuser.ui.favorite.FavoriteUser
import com.jonathan.mygithubsearchuser.ui.helper.ViewModelFactorySetting

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactorySetting(pref)).get(
            ThemeViewModel::class.java
        )
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        supportActionBar?.hide()
        val mainViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.listUser.observe(this) { listUser ->
            setReviewData(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    val search = searchBar.text.toString()
                    mainViewModel.findUser(search)
                    false
                }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menu_1 -> {
                    val intent = Intent(this@MainActivity, FavoriteUser::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_2 -> {
                    val intent = Intent(this@MainActivity, ThemeSettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setReviewData(consumerReviews: List<ItemsItem>) {
        val adapter = ListGithubAdapter()
        adapter.submitList(consumerReviews)
        binding.rvUsers.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

package com.jonathan.mygithubsearchuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jonathan.mygithubsearchuser.data.database.FavoriteUser
import com.jonathan.mygithubsearchuser.data.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val  mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    val readAllData: LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllUsers()

}
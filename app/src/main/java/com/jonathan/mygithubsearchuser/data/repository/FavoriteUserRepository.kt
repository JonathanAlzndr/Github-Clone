package com.jonathan.mygithubsearchuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.jonathan.mygithubsearchuser.data.database.FavoriteUser
import com.jonathan.mygithubsearchuser.data.database.FavoriteUserDao
import com.jonathan.mygithubsearchuser.data.database.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.FavoriteUserDao()
    }

    fun getAllUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllUsers()

    fun insert(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(user) }
    }

    fun delete(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(user) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return mFavoriteUserDao.getFavoriteUserByUsername(username)
    }
}
package com.jonathan.mygithubsearchuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)

    @Delete
    fun delete(user: FavoriteUser)

    @Query("SELECT * from FavoriteUser ORDER BY username ASC")
    fun getAllUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>
}
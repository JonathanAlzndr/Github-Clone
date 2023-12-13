package com.jonathan.mygithubsearchuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathan.mygithubsearchuser.data.database.FavoriteUser
import com.jonathan.mygithubsearchuser.data.repository.FavoriteUserRepository
import com.jonathan.mygithubsearchuser.data.response.DetailUserResponse
import com.jonathan.mygithubsearchuser.data.response.ItemsItem
import com.jonathan.mygithubsearchuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUserResponse = MutableLiveData<DetailUserResponse>()
    val detailUserResponse: LiveData<DetailUserResponse> = _detailUserResponse

    private val _isLoadingFragment = MutableLiveData<Boolean>()
    val isLoadingFragment: LiveData<Boolean> = _isLoadingFragment

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun showUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _detailUserResponse.value = response.body()
                } else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun showFollowers(username: String) {
        _isLoadingFragment.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFragment.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e("DetailFragmentViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFragment.value = false
                Log.e("DetailFragmentViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun showFollowing(username: String) {
        _isLoadingFragment.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFragment.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e("DetailFragmentViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFragment.value = false
                Log.e("DetailFragmentViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun insert(user: FavoriteUser) {
        mFavoriteUserRepository.insert(user)
    }

    fun delete(user: FavoriteUser) {
        mFavoriteUserRepository.delete(user)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return mFavoriteUserRepository.getFavoriteUserByUsername(username)
    }

    fun getUsernameAvatar(): FavoriteUser {
        val username = _detailUserResponse.value?.login ?: "Anonymous"
        val avatarUrl = _detailUserResponse.value?.avatarUrl
        return FavoriteUser(username, avatarUrl)
    }
}
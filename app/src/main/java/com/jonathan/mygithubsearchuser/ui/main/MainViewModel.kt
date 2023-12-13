package com.jonathan.mygithubsearchuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathan.mygithubsearchuser.data.response.GithubResponse
import com.jonathan.mygithubsearchuser.data.response.ItemsItem
import com.jonathan.mygithubsearchuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _githubResponse = MutableLiveData<GithubResponse>()
    val githubResponse: LiveData<GithubResponse> = _githubResponse

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUser(SEARCH)
    }

     fun findUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _githubResponse.value = response.body()
                    _listUser.value = response.body()?.items
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val SEARCH = "Jonathan"
    }
}
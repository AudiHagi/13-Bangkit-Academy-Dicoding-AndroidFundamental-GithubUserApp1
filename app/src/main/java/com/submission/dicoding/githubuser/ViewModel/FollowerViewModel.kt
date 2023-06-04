package com.submission.dicoding.githubuser.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.dicoding.githubuser.Api.ApiConfig
import com.submission.dicoding.githubuser.GSONFile.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private val _userFollowers = MutableLiveData<ArrayList<GithubUser>>()
    val userFollowers: LiveData<ArrayList<GithubUser>> = _userFollowers

    companion object {
        private const val TAG = "MainActivity"
    }

    fun getUserFollowers(username: String) {
        val client = ApiConfig.getApiService().getUserFollower(username)
        client.enqueue(object : Callback<ArrayList<GithubUser>> {
            override fun onResponse(
                call: Call<ArrayList<GithubUser>>,
                response: Response<ArrayList<GithubUser>>
            ) {
                if (response.isSuccessful) {
                    _userFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubUser>>, t: Throwable) {
                Log.e(TAG, "Failure: ${t.message}")
            }
        })
    }
}
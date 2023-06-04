package com.submission.dicoding.githubuser.Api

import com.submission.dicoding.githubuser.GSONFile.GithubResponse
import com.submission.dicoding.githubuser.GSONFile.GithubUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    @Headers("Authorization: token insert_your_github_token_here")
    fun getAllUser(): Call<GithubResponse>

    @GET("search/users")
    @Headers("Authorization: token insert_your_github_token_here")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token insert_your_github_token_here")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<GithubUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token insert_your_github_token_here")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ArrayList<GithubUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token insert_your_github_token_here")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<GithubUser>>
}
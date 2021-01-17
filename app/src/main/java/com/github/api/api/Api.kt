package com.github.api.api

import com.github.api.data.model.DetailUserResponse
import com.github.api.data.model.User
import com.github.api.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token  8e233eb62a7cb3e61096964e2ae1a811c962f5a8")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token  8e233eb62a7cb3e61096964e2ae1a811c962f5a8")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token  8e233eb62a7cb3e61096964e2ae1a811c962f5a8")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token  8e233eb62a7cb3e61096964e2ae1a811c962f5a8")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}
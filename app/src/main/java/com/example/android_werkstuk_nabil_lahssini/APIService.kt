package com.example.android_werkstuk_nabil_lahssini

import com.example.android_werkstuk_nabil_lahssini.Entities.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/3/search/movie?page=1")
    fun listMovies(@Query("query") search: String, @Query("api_key") api_key: String): Call<Results>
}
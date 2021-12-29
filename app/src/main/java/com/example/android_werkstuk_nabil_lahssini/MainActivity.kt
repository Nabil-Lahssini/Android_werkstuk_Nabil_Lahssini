package com.example.android_werkstuk_nabil_lahssini

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_werkstuk_nabil_lahssini.Entities.Movie
import com.example.android_werkstuk_nabil_lahssini.Entities.Results
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    companion object  {
        const val URL_MOVIE_API ="https://api.themoviedb.org"
        const val API_KEY = "6bb070fa1158aaf98c19a06bb4ce9284"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //searchMovies("Spider")
    }

    fun searchMovies(query: String) {
        val retro = Retrofit.Builder()
            .baseUrl(URL_MOVIE_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retro.create(APIService::class.java)
        val moviereq = service.listMovies(search = query, api_key = API_KEY)
        moviereq.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>){
                val allMovies = response.body()
                Log.v(MainActivity::class.simpleName, allMovies!!.results[0].overview)
            }
            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "on FAILURE!!!!")
            }
        })
    }

}
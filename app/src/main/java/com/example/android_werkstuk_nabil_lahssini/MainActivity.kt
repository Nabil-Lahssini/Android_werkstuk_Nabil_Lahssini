package com.example.android_werkstuk_nabil_lahssini

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.widget.addTextChangedListener
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
        val searchtext = findViewById<EditText>(R.id.searchText)
        searchtext.addTextChangedListener {
            val search = searchtext.text.toString()
            if (search.isNotEmpty() && search.isNotBlank()) {
                searchMovies(search)
            }
        }
    }

    fun searchMovies(query: String) {
        val listview = findViewById<ListView>(R.id.listView)
        val contextss = this
        var titles = arrayListOf<String>()
        var descriptions = arrayListOf<String>()
        val retro = Retrofit.Builder()
            .baseUrl(URL_MOVIE_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retro.create(APIService::class.java)
        val moviereq = service.listMovies(search = query, api_key = API_KEY)
        moviereq.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>){
                val allMovies = response.body()
                for (movie in allMovies!!.results){
                    titles.add(movie.title)
                    descriptions.add(movie.overview)
                }
                val myListAdapter = MyListAdapter(contextss, title = titles, description = descriptions)
                for (title in titles){
                    Log.i(MainActivity::class.simpleName, title)
                }
                listview.adapter = myListAdapter
            }
            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "on FAILURE!!!!")
            }

        })


    }

}
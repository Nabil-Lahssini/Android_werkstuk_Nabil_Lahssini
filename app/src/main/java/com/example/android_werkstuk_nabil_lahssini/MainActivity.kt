package com.example.android_werkstuk_nabil_lahssini

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import com.example.android_werkstuk_nabil_lahssini.Entities.Results
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import android.widget.TextView

import android.widget.AdapterView

import android.widget.AdapterView.OnItemClickListener

import android.widget.ArrayAdapter
import com.example.android_werkstuk_nabil_lahssini.Entities.Genres
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {
    lateinit var listview : ListView
    val db : DatabaseHelper = DatabaseHelper(this)
    companion object  {
        const val URL_MOVIE_API ="https://api.themoviedb.org"
        const val API_KEY = "6bb070fa1158aaf98c19a06bb4ce9284"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchtext = findViewById<EditText>(R.id.searchText)
        val dashboardButton = findViewById<Button>(R.id.dashboardButton)
        listview = findViewById<ListView>(R.id.listView)
        searchtext.addTextChangedListener {
            val search = searchtext.text.toString()
            if (search.isNotEmpty() && search.isNotBlank()) {
                val runnable = Runnable {
                    searchMovies(searchtext.text.toString())
                }
                runnable.run()
            }else{
                val myListAdapter = MyListAdapter(this,
                    title = arrayListOf<String>(),
                    description = arrayListOf<String>(),
                    _id = arrayListOf<Int>(),
                    _release_date = arrayListOf<String>(),
                    _runtime = arrayListOf<Int>()
                )
                listview.adapter = myListAdapter
            }
        }
        dashboardButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        listview.setOnItemClickListener { parent, view, position, id ->
            val title = parent.getItemAtPosition(position)// The item that was clicked
            Toast.makeText(this, title.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    fun searchMovies(query: String) {
        val contextss = this
        var ids = arrayListOf<Int>()
        var titles = arrayListOf<String>()
        var runtime = arrayListOf<Int>()
        var descriptions = arrayListOf<String>()
        var release_date = arrayListOf<String>()
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
                    ids.add(movie.id)
                    release_date.add(movie.release_date)
                    runtime.add(movie.runtime)
                }
                listview.adapter = MyListAdapter(
                    context =  contextss, title = titles, description = descriptions, _id = ids, _release_date = release_date, _runtime = runtime)

                listview.onItemClickListener =
                    OnItemClickListener { parent, view, position, id ->
                        var id = view.findViewById(R.id.id) as TextView
                        val title = view.findViewById(R.id.title) as TextView
                        val overview = view.findViewById(R.id.description) as TextView
                        val release_date = view.findViewById(R.id.release_date) as TextView
                        val runtime = view.findViewById(R.id.runtime) as TextView
                        db.addMovie(id.text.toString().toInt(),
                            title.text.toString(),
                            overview.text.toString(),
                            runtime.text.toString().toInt(),
                            release_date.text.toString())
                    }
            }
            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "on FAILURE!!!!")
            }
        })
    }

}
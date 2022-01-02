package com.example.android_werkstuk_nabil_lahssini.ui.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_werkstuk_nabil_lahssini.*
import com.example.android_werkstuk_nabil_lahssini.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    lateinit var listview: ListView;
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        listview = binding.root.findViewById<ListView>(R.id.listView)

        refresh()
        val addbutton = binding.root.findViewById<Button>(R.id.addButton)
        addbutton.setOnClickListener {
            val intent = Intent(this.context as Activity, MainActivity::class.java)
            startActivity(intent)
        }
        val db : DatabaseHelper = DatabaseHelper(this.context as Activity)
        listview.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                var id = view.findViewById(R.id.id) as TextView
                db.deleteOneRow(
                    id.text as String
                )
                refresh()
            }
        val root: View = binding.root
        return root
    }

    fun refresh() {
        val db = DatabaseHelper(this.context as Activity)
        val movies = db.readAllData();

        val ids = arrayListOf<Int>()
        val description = arrayListOf<String>()
        val title = arrayListOf<String>()
        val release_date = arrayListOf<String>()
        val runtime = arrayListOf<Int>()

        for ( movie in movies){
            ids.add(movie.id)
            description.add(movie.overview)
            release_date.add(movie.release_date)
            title.add(movie.title)
            runtime.add(movie.runtime)
        }

        val myListAdapter = MyListAdapter(
            this.context as Activity,
            title = title,
            description = description,
            _id = ids,
            _release_date = release_date,
            _runtime = runtime
        )
        listview.adapter = myListAdapter
    }
    override fun onResume() {  // After a pause OR at startup
        super.onResume()
        refresh()
        //Refresh your stuff here
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
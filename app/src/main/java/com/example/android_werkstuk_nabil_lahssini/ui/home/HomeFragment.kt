package com.example.android_werkstuk_nabil_lahssini.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android_werkstuk_nabil_lahssini.DatabaseHelper
import com.example.android_werkstuk_nabil_lahssini.R
import com.example.android_werkstuk_nabil_lahssini.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        refresh()
        val root: View = binding.root

        return root
    }

    fun refresh(){
        val db = DatabaseHelper(this.context as Activity)
        val hourview = binding.root.findViewById<TextView>(R.id.hourView)
        val movies = db.readAllData()
        var hours: Int = 0;
        for (movie in movies){
            hours += movie.runtime
            print(movie.runtime.toString())
        }
        hours /= 60
        hourview.text = hours.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
}
package com.example.android_werkstuk_nabil_lahssini

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.w3c.dom.Text

class MyListAdapter (private val context: Activity,
                     private val title:ArrayList<String>,
                     private val description: ArrayList<String>,
                     private val _id: ArrayList<Int>,
                     private val _release_date: ArrayList<String>,
                     private val _runtime: ArrayList<Int>
                     ) : ArrayAdapter<String>(context, R.layout.custom_list, title) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val subtitleText = rowView.findViewById(R.id.description) as TextView
        val id = rowView.findViewById(R.id.id) as TextView
        val release_date = rowView.findViewById(R.id.release_date) as TextView
        val runtime = rowView.findViewById(R.id.runtime) as TextView

        titleText.text = title[position]
        release_date.text = _release_date[position]
        subtitleText.text = description[position]
        id.text = _id[position].toString()
        runtime.text = _runtime[position].toString()
        return rowView
    }
}
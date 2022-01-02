package com.example.android_werkstuk_nabil_lahssini.Entities

data class DbMovie(

    val id : Int,
    val title : String,
    val overview : String,
    val runtime : Int = 90,
    val release_date : String
)
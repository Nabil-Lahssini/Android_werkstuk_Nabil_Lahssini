package com.example.android_werkstuk_nabil_lahssini

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    val db : DatabaseHelper = DatabaseHelper(InstrumentationRegistry.getInstrumentation().targetContext)
    @Before
    fun putData(){
        db.addMovie(1234567890, "my_title", "overview", 90, "12/12/2012")
    }
    @Test
    fun testDb(){
        val movies = db.readAllData()
        var bool = false
        for (movie in movies){
            if (movie.id == 1234567890 && movie.title == "my_title"){
                bool = true
            }
        }
        assertEquals(true, bool)
    }
    @After
    fun clearDb(){
        db.deleteOneRow("1234567890")
    }
}
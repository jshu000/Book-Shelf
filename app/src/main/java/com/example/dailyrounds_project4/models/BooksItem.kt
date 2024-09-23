package com.example.dailyrounds_project4.models

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class BooksItem(
    val id: String,
    val image: String,
    val popularity: Int,
    val publishedChapterDate: Int,
    val score: Double,
    val title: String
){
    // Function to get the year from the Unix timestamp
    fun getPublishedYear(): Int {
        val date = Date(publishedChapterDate * 1000L)
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        val year = calendar.get(Calendar.YEAR)
        return year
    }
}
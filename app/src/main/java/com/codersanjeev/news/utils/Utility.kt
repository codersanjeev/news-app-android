package com.codersanjeev.news.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object

    fun getTabTitles() =
        listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
}

fun String.toDate(): Date? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    var date: Date? = null
    try {
        date = dateFormat.parse(this)
    } catch (exception: ParseException) {
        exception.printStackTrace()
    }
    return date
}

// November 6, 2014 2:45 PM
fun Date.toFormattedString(): String {
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy hh.mm aa", Locale.getDefault())
    return dateFormat.format(this)
}

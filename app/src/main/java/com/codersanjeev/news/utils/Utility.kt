package com.codersanjeev.news.utils

import java.util.*

class Utility {
    companion object

    fun getTabTitles() =
        listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
}

// 2021-01-08T12:56:12Z
fun String.toDate(): Date {
    // TODO: fix this soon
    return Date()
}

// November 6, 2014 2:45 PM
fun Date.toFormattedString(): String {
    // TODO: fix this soon
    return ""
}

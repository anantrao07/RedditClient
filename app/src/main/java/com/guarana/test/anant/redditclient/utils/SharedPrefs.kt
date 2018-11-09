package com.guarana.test.anant.redditclient.utils

import android.content.Context
import android.content.SharedPreferences
import com.guarana.test.anant.redditclient.application.RedditApplication.Companion.applicationContext

/**
 * Created by anant on 2018-11-09.
 */

fun getSharedPrefs(): SharedPreferences {
    return applicationContext().getSharedPreferences(
            "com.guarana.test.anant.redditclient", Context.MODE_PRIVATE)
}

fun LoggedIn(isloggedIn: Boolean) {

    val editor = getSharedPrefs().edit()
    editor.putBoolean("isLoggedIn", isloggedIn)
    editor.apply()
}

fun ifLoggedIn(): Boolean {

    return getSharedPrefs().getBoolean("isLoggedIn", false)
}
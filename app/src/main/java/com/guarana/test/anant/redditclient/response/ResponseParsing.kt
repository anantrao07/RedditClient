package com.guarana.test.anant.redditclient.response

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.guarana.test.anant.redditclient.persistance.RedditChildrenData
import okhttp3.ResponseBody

/**
 * Created by anant on 2018-11-08.
 */
class ParseException(val reason: String): RuntimeException(reason)

private const val TAG = "RESPONSEPARSING"

@Suppress("UNUSED_PARAMETER")
fun parseEmpty(responseBody: ResponseBody) {}



fun parseRedditItems(responseBody: ResponseBody): RedditChildrenData {

    val responseString = responseBody.string()

    val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    try {
        val redditItemsListData = gson.fromJson(responseString, RedditItemsListResponseData::class.java)

        return redditItemsListData.data
    } catch (e: Exception) {
        Log.d(TAG, e.localizedMessage)
    }
    throw ParseException("Couldn't parse item details ")
}

data class RedditItemsListResponseData
(
        @SerializedName("data") val data: RedditChildrenData

)

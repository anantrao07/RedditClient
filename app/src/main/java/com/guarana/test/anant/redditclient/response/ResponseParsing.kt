package com.guarana.test.anant.redditclient.response

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.guarana.test.anant.redditclient.persistance.models.*
import io.realm.RealmList
import okhttp3.ResponseBody
import com.google.gson.reflect.TypeToken



/**
 * Created by anant on 2018-11-08.
 */
class ParseException(val reason: String): RuntimeException(reason)

private const val TAG = "RESPONSEPARSING"

@Suppress("UNUSED_PARAMETER")
fun parseEmpty(responseBody: ResponseBody) {
}

fun parseRedditItems(responseBody: ResponseBody): RealmList<RedditResponseChildrenData> {

    val responseString = responseBody.string()

    val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    try {
        val redditItemsListData = gson.fromJson(responseString, RedditItemsListResponseData::class.java)

        return redditItemsListData.data.children
    } catch (e: Exception) {
        Log.d(TAG, e.localizedMessage)
    }
    throw ParseException("Couldn't parse Reddit items list ")
}

fun parseRedditPostItemDetail(responseBody: ResponseBody): List<RedditPostItemDetailResponseData> /*RealmList<RedditPostItemDetailData>*/ {

    val responseString = responseBody.string()

    val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    try {
        val redditPostItemDetails : List<RedditPostItemDetailResponseData> = gson.fromJson(responseString, Array<RedditPostItemDetailResponseData>::class.java).toList()
        return redditPostItemDetails

    } catch (e: Exception) {
        Log.d(TAG, e.localizedMessage)
    }
    throw ParseException("Could'nt parse Reddit post Item Details ")
}

data class RedditItemsListResponseData
(
        @SerializedName("data") val data: RedditResponseChildren

)

data class RedditPostItemDetailResponseData
(
        @SerializedName("data") val data: RedditPostItemDetailResponse
)

package com.guarana.test.anant.redditclient.persistance

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by anant on 2018-11-08.
 */


open class RedditResponse
(
        @SerializedName("data") var data: RedditChildrenData?= null
)
open class RedditChildrenData
(
        @SerializedName("children") var children: RealmList<RedditChildrenItemData>?= null

): RealmObject()

open class RedditChildrenItemData
(
        @SerializedName("data") var data: RedditItemData?= null
):RealmObject()

open class RedditItemData
(

        var thumbNail: String = "",
        var author: String = "",
        var title: String = ""
): RealmObject()


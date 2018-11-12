package com.guarana.test.anant.redditclient.persistance.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by anant on 2018-11-08.
 */

open class RedditResponseChildren
(
        @SerializedName("children") var children: RealmList<RedditResponseChildrenData> = RealmList()
) : RealmObject()

open class RedditResponseChildrenData
(
        @SerializedName("data") var childrenData: RedditItemData? = RedditItemData()
) : RealmObject()

open class RedditItemData
(
        @PrimaryKey
        var id: String = "",
        var thumbnail: String = "",
        var author: String = "",
        var title: String = "",
        var permalink: String = ""
) : RealmObject()





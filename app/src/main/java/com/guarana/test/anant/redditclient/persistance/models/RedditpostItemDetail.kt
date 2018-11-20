package com.guarana.test.anant.redditclient.persistance.models

import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by anant on 2018-11-11.
 */

open class RedditPostItemDetailResponse
(
        @SerializedName("children")var children: RealmList<RedditPostItemDetailData> = RealmList()
) : RealmObject()

open class RedditPostItemDetailData
(
        @SerializedName("data")var data: RedditPostItemDetail? = RedditPostItemDetail()
): RealmObject()

open class RedditPostItemDetail
(
        @PrimaryKey
        var id: String = "",
        var title: String = "",
        var author: String = "",
        var body: String = "",
        @SerializedName("preview")
        var url: RedditPostItemDetailImage? = RedditPostItemDetailImage()
): RealmObject()

open class RedditPostItemDetailImage
(
        @SerializedName("images") var images: RealmList<RedditPostItemDetailImageSource>? = RealmList()
): RealmObject()

open class RedditPostItemDetailImageSource
(
        @SerializedName("source") var source: RedditPostItemDetailImageUrl? = null
):RealmObject()

open class RedditPostItemDetailImageUrl
(
        @SerializedName("url") var url: String = "",
        @PrimaryKey
        var id: String = ""
): RealmObject()
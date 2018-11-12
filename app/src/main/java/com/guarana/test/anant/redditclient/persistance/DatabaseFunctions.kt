package com.guarana.test.anant.redditclient.persistance


import com.guarana.test.anant.redditclient.persistance.models.RedditItemData
import com.guarana.test.anant.redditclient.persistance.models.RedditPostItemDetail
import com.guarana.test.anant.redditclient.persistance.models.RedditPostItemDetailData
import com.guarana.test.anant.redditclient.persistance.models.RedditResponseChildrenData
import com.guarana.test.anant.redditclient.response.RedditPostItemDetailResponseData
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.kotlin.where

/**
 * Created by anant on 2018-11-08.
 */
fun store(entity: RealmObject) {
    val realm = Realm.getDefaultInstance()
    realm.executeTransaction {
        it.copyToRealm(entity)
    }
    realm.close()
}

fun storeOrUpdate(entities: List<RealmObject>) {
    val realm = Realm.getDefaultInstance()
    realm.executeTransaction {
        it.copyToRealmOrUpdate(entities)
    }
    realm.close()
}

fun storeOrUpdate(entity: RealmObject) {
    val realm = Realm.getDefaultInstance()
    realm.executeTransaction {
        it.copyToRealmOrUpdate(entity)
    }
    realm.close()
}

fun storeRedditChildren(redditChildren: RealmList<RedditResponseChildrenData>) {
    redditChildren.forEach {
        storeOrUpdate(it.childrenData as RealmObject)
    }
}

fun storeRedditPostItemDetail(redditPostItemDetail: List<RedditPostItemDetailResponseData>) {
    redditPostItemDetail.forEach {
        it.data.children.forEach {
            storeOrUpdate(it.data as RealmObject)
        }
    }
}

@Suppress("UNUSED_PARAMETER")
fun storeEmpty(unit: Unit) {}

fun fetchRedditItems(realm: Realm): RealmResults<RedditItemData>? {
    return realm.where<RedditItemData>()
            .findAll()
}

fun fetchRedditPostDetailItem(realm: Realm, itemId: String): RedditPostItemDetail? {

    return realm.where<RedditPostItemDetail>()
            .equalTo("id",itemId)
            .findFirst()
}




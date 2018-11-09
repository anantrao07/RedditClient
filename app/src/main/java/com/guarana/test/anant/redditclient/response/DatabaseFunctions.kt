package com.guarana.test.anant.redditclient.response

import com.guarana.test.anant.redditclient.persistance.RedditChildrenData
import io.realm.Realm
import io.realm.RealmObject

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


fun storeRedditChildrenItemData(redditItemData: List<RedditChildrenData>) {

    if (redditItemData != null &&  redditItemData.isNotEmpty()) {
        redditItemData.forEach{
            it.children!!.forEach {
                store(it.data!!)
            }
        }
    }

}

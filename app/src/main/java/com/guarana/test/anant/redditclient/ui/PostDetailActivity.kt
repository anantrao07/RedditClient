package com.guarana.test.anant.redditclient.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.guarana.test.anant.redditclient.R
import com.guarana.test.anant.redditclient.networking.NetworkManager
import com.guarana.test.anant.redditclient.persistance.fetchRedditPostDetailItem
import com.guarana.test.anant.redditclient.persistance.models.RedditPostItemDetail
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.content_post_detail.*
import kotlinx.android.synthetic.main.reddit_client_list_item.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class PostDetailActivity : AppCompatActivity() {

    private val realmInstance = Realm.getDefaultInstance()
    private var redditPostItemDetailDataResult: RedditPostItemDetail? = null
    private var itemId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        setSupportActionBar(toolbar)

        itemId = intent.getStringExtra("itemID")


        try {
            launch(UI) {

                NetworkManager.suspendRequestRedditPostItemDetail(intent.getStringExtra("permalink"))
            }
        } catch (e: Exception) {
            Log.d("item detail", e.localizedMessage)
        }
    }

    override fun onResume() {
        super.onResume()
        redditPostItemDetailDataResult = fetchRedditPostDetailItem(realmInstance, itemId)

        Log.d("itemID", itemId)

        if (redditPostItemDetailDataResult != null) {
            postDetailAuthorTextView.text = redditPostItemDetailDataResult?.author
            postTitleTextView.text = redditPostItemDetailDataResult?.title
            postBodyTextView.text = redditPostItemDetailDataResult?.body
            val url = redditPostItemDetailDataResult?.url?.images?.forEach {
                it.source?.url
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realmInstance.close()
        redditPostItemDetailDataResult = null
    }
}

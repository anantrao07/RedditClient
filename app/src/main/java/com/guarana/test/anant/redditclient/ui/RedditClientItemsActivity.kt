package com.guarana.test.anant.redditclient.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.guarana.test.anant.redditclient.R
import com.guarana.test.anant.redditclient.networking.NetworkManager.suspendRequestRedditItems

import kotlinx.android.synthetic.main.activity_reddit_client_item.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class RedditClientItemsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit_client_item)
        setSupportActionBar(toolbar)

        async(UI) {
            try {
                suspendRequestRedditItems()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

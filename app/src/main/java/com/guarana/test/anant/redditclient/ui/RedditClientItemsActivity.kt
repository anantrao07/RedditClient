package com.guarana.test.anant.redditclient.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guarana.test.anant.redditclient.R
import com.guarana.test.anant.redditclient.networking.NetworkManager.suspendRequestRedditItems
import com.guarana.test.anant.redditclient.persistance.fetchRedditItems
import com.guarana.test.anant.redditclient.persistance.models.RedditItemData
import com.squareup.picasso.Picasso
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_reddit_client_item.*
import kotlinx.android.synthetic.main.content_reddit_client_item.*
import kotlinx.android.synthetic.main.reddit_client_list_item.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class RedditClientItemsActivity : AppCompatActivity() {

    private var realmInstance = Realm.getDefaultInstance()
    private var redditItemsResults: RealmResults<RedditItemData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit_client_item)
        setSupportActionBar(toolbar)

        redditItemsResults = fetchRedditItems(realmInstance)
        launch {
            async(UI) {
                try {
                    suspendRequestRedditItems()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.await()
        }


        if (redditItemsResults?.isNotEmpty() == true) {
           setUpRecyclerView(redditItemsResults!!)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        realmInstance.close()
    }

    private fun setUpRecyclerView(redditItems: RealmResults<RedditItemData>) {
        redditItemsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        redditItemsRecyclerView.adapter = RedditItemsAdapter(redditItems)
    }



    inner class RedditItemsAdapter(private val redditItemsData: OrderedRealmCollection<RedditItemData>) : RealmRecyclerViewAdapter<RedditItemData,RedditItemsAdapter.RedditItemViewHolder>(redditItemsData, true) {
        inner class RedditItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditItemViewHolder {

            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.reddit_client_list_item, parent, false)
            return RedditItemViewHolder(inflatedView)
        }

        override fun onBindViewHolder(holder: RedditItemViewHolder, position: Int) {
            getItem(position)?.let { redditItem ->
                holder.itemView.postTitleTextView.text = redditItem.title.trim()
                holder.itemView.postAuthorTextView.text = redditItem.author.trim()
                if (redditItem.thumbnail.isNotBlank())
                Picasso.get()
                        .load(redditItem.thumbnail)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(holder.itemView.itemImageView)

                holder.itemView.setOnClickListener {
                   /* try {
                        async(UI) {

                            NetworkManager.suspendRequestRedditPostItemDetail( redditItem.permalink)
                        }

                    }catch (e:Exception){
                       Log.d("item detail",e.localizedMessage)
                    }*/
                    val itemDetailIntent = Intent(applicationContext, PostDetailActivity::class.java)
                    itemDetailIntent.putExtra("itemID", redditItem.id)
                    itemDetailIntent.putExtra("permalink",redditItem.permalink)
                    startActivity(itemDetailIntent)
                }
            }
        }
    }
}

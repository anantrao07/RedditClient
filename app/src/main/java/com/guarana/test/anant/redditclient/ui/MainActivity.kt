package com.guarana.test.anant.redditclient.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.guarana.test.anant.redditclient.R
import com.guarana.test.anant.redditclient.utils.LoggedIn
import com.guarana.test.anant.redditclient.utils.ifLoggedIn
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ifLoggedIn()) {
            val startItemPage = Intent(this, RedditClientItemsActivity::class.java)
            startActivity(startItemPage)
            finish()
        } else {
            loginButton.setOnClickListener {
                when {
                    userNameLoginText.text.isNullOrEmpty() -> userNameLoginText.error = "Please Enter username"
                    validateUserName(userNameLoginText.text.toString()) -> {
                        val startItemPage = Intent(this, RedditClientItemsActivity::class.java)
                        startActivity(startItemPage)
                        LoggedIn(true)

                    }
                    else -> userNameLoginText.error = "User Name must contain only alphabets"
                }
            }
        }
    }

    private fun validateUserName(userName: String): Boolean {
        return userName.matches(Regex("[a-zA-Z]+"))
    }
}

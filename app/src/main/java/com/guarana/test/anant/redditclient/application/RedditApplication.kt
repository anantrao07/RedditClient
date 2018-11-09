package com.guarana.test.anant.redditclient.application

import android.app.Application
import android.content.Context
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by anant on 2018-11-08.
 */

class RedditApplication : Application() {
    companion object {
        var instance: RedditApplication? = null
            private set

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        // init Realm database
        Realm.init(this)
        val configBuilder = RealmConfiguration.Builder()
                .name("redditApplication.realm")
                .schemaVersion(1)
                .build()

        Realm.setDefaultConfiguration(configBuilder)

        if (BuildConfig.BUILD_TYPE == "debug") {
            configBuilder.shouldDeleteRealmIfMigrationNeeded()
        }
    }
}
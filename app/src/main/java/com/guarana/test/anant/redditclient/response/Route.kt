package com.guarana.test.anant.redditclient.response

import com.guarana.test.anant.redditclient.networking.API
import com.guarana.test.anant.redditclient.networking.NetworkManager
import com.guarana.test.anant.redditclient.networking.RequestParameters
import okhttp3.HttpUrl
import okhttp3.Request

/**
 * Created by anant on 2018-11-08.
 */
sealed class Route(val path: String) {

    open fun getUrl(): HttpUrl {
        return HttpUrl.get(NetworkManager.api.baseURL)!!.newBuilder()
                .addPathSegments(path)
                .build()
    }

    fun makeRequest(api: API, params: RequestParameters?): Request {
        val requestMethod = "GET"
        return makeRequest(api, params, requestMethod)
    }

    private fun makeRequest(api: API, params: RequestParameters?, requestMethod: String): Request {

        val requestBuilder = Request.Builder()
                .url(getUrl())
        return requestBuilder.build()
    }
}

class GetAllRedditItems : Route("/r/all.json") {
    override fun getUrl(): HttpUrl {
        var allRedditItemsUrl = "${NetworkManager.api.baseURL}" +
                "/r/all.json"
        return HttpUrl.parse(allRedditItemsUrl)!!
    }
}

class GetRedditPostItemDetail(var permalink: String) : Route("") {

    override fun getUrl(): HttpUrl {

        val redditPostItemUrl = "${NetworkManager.api.baseURL}"+ "$permalink.json"
        return HttpUrl.parse(redditPostItemUrl)!!

    }
}
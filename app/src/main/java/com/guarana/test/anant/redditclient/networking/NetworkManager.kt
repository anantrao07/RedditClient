package com.guarana.test.anant.redditclient.networking

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.guarana.test.anant.redditclient.persistance.storeEmpty
import com.guarana.test.anant.redditclient.persistance.storeRedditChildren
import com.guarana.test.anant.redditclient.persistance.storeRedditPostItemDetail
import com.guarana.test.anant.redditclient.response.*
import com.guarana.test.anant.redditclient.response.Route
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.android.HandlerContext
import kotlinx.coroutines.experimental.run
import okhttp3.*
import java.io.IOException
import java.net.URI

/**
 * Created by anant on 2018-11-08.
 */

class API private constructor(var baseURL: URI) {
    companion object {
        val baseRoute: API = API(URI.create("https://www.reddit.com"))
    }
}

object NetworkManager {
    var api: API = API.baseRoute
    private const val TAG = "NetworkingManager"
    private val networkRequestContext: HandlerContext
    private val networkRequestHandlerThread = HandlerThread("request.looper")

    init {
        networkRequestHandlerThread.start()
        networkRequestContext = HandlerContext(Handler(networkRequestHandlerThread.looper), "networkRequestContext")
    }

    private suspend fun <T> suspendRequest(
            route: Route,
            params: RequestParameters?,
            parse: (body: ResponseBody) -> T,
            store: (T) -> Unit): T = run(networkRequestContext)
    {
        val result: T?
        try {
            // fetch
            val responseBody = suspendRequest(route, params)

            // parse
            val parsedResponse = parse(responseBody)

            // store
            store(parsedResponse)

            Log.d("Success:", route.path)

            result = parsedResponse
        } catch (e: Exception) {
            Log.d("failure:", route.path)
            Log.d(TAG, e.localizedMessage)
            throw e
        } catch (e: Exception) {
            Log.d("failure:", route.path)
            Log.e(TAG, e.localizedMessage)
            throw e
        }
        result!!
    }

    private suspend fun suspendRequest(route: Route, params: RequestParameters?): ResponseBody = run(networkRequestContext) {
        var responseBody: ResponseBody?

        try {
            val request = route.makeRequest(api,params)

            responseBody = OkHttpClient().newCall(request).suspendEnqueue()
        } catch (e: Exception) {
            e.localizedMessage
            throw e
        }
        responseBody!!
    }

    private suspend fun Call.suspendEnqueue(): ResponseBody {
        val deferred = CompletableDeferred<ResponseBody>()

        this.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()
                if (body != null) {
                    if (!response.isSuccessful) {

                        deferred.completeExceptionally(RequestException(response.message(), response.code()))
                    } else {
                        deferred.complete(body)
                    }
                } else {
                    deferred.completeExceptionally(RequestException("Missing response body", -1))
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                deferred.completeExceptionally(e)
            }
        })

        return deferred.await()
    }

    suspend fun suspendRequestRedditItems() {
        suspendRequest(GetAllRedditItems(), null, ::parseRedditItems, ::storeRedditChildren)
    }

    suspend fun suspendRequestRedditPostItemDetail(permalink: String){

        suspendRequest(GetRedditPostItemDetail(permalink), null, ::parseRedditPostItemDetail, ::storeRedditPostItemDetail)

    }
}

class RequestException(reason: String, val code: Int) : RuntimeException(reason)

@Suppress("UNUSED_PARAMETER")
fun storeEmptyWithoutParameter(any: Any) {
}
package com.appfactory.data.source.remote

import android.content.Context
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * CACHE SIZE 10MB
 */
private const val CACHE_SIZE = (10 * 1024 * 1024).toLong()

class ServiceGenerator {

    companion object {
        /**
         * Creates generic service
         */
        fun <S> createService(
            serviceClass: Class<S>,
            url: String,
            context: Context
        ): S {
            val okHttpClient = OkHttpClient.Builder()
                // cache storage
                .cache(
                    Cache(
                        context.cacheDir,
                        CACHE_SIZE
                    )
                )
                // Add an Interceptor to the OkHttpClient.
                .addInterceptor { chain ->
                    // Get the request from the chain.
                    var request = chain.request()
                    /*
                    *  we initialize the request and change its header depending on whether
                    *  the device is connected to Internet or not.
                    */
                    request = if (context.hasInternet()) {
                        /*
                  *  If there is Internet, get the cache that was stored 5 seconds ago.
                  *  If the cache is older than 5 seconds, then discard it,
                  *  and indicate an error in fetching the response.
                  *  The 'max-age' attribute is responsible for this behavior.
                  */
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                    } else {
                        /*
                 *  If there is no Internet, get the cache that was stored 7 days ago.
                 *  If the cache is older than 7 days, then discard it,
                 *  and indicate an error in fetching the response.
                 *  The 'max-stale' attribute is responsible for this behavior.
                 *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                 */
                        request.newBuilder().header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=$CACHE_SIZE"
                        ).build()
                    }
                    // Add the modified request to the chain.
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(serviceClass)
        }
    }
}
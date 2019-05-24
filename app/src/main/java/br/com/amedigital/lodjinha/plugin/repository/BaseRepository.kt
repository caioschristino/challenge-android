package br.com.amedigital.lodjinha.plugin.repository

import android.util.Log

abstract class BaseRepository {
    val cacheMap: MutableMap<String, Cache<*>> = mutableMapOf()

    fun <R> cacheOrApi(cacheName: String, api:()->R): R? {
        if(cacheMap[cacheName] == null || cacheMap[cacheName]!!.isCacheObsolete()) {
            val cache = Cache<R>()
            cache.lastData = api.invoke()
            cacheMap[cacheName] = cache
            Log.w("REQUEST", "got $cacheName from API")
        } else {
            Log.w("REQUEST", "got $cacheName from cache")
        }
        return cacheMap[cacheName]!!.lastData as? R
    }
}
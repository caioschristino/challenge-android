package br.com.amedigital.lodjinha.plugin.repository

abstract class BaseRepository {
    val cacheMap: MutableMap<String, Cache<*>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <R> cacheOrApi(cacheName: String, api:()->R): R? {
        if(cacheMap[cacheName] == null || cacheMap[cacheName]!!.isCacheObsolete()) {
            val cache = Cache<R>()
            cache.lastData = api.invoke()
            cacheMap[cacheName] = cache
        }
        return cacheMap[cacheName]!!.lastData as? R
    }
}
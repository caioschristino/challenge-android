package br.com.amedigital.lodjinha.plugin.repository

import java.util.concurrent.TimeUnit

data class CacheDef(val timeout: Long = 10, val timeunit: TimeUnit = TimeUnit.MINUTES)

class Cache<V>(var cacheDef: CacheDef = CacheDef()) {
    private var data: V? = null
    private var lastupdated: Long = 0

    var lastData: V?
        get() = data
        set(value) {
            data = value
            lastupdated = System.currentTimeMillis()
        }

    fun isCacheObsolete(): Boolean {
        val(timeout, timeunit) = cacheDef
        val diff = System.currentTimeMillis() - lastupdated
        return timeunit.convert(diff, TimeUnit.MILLISECONDS) >= timeout
    }
}
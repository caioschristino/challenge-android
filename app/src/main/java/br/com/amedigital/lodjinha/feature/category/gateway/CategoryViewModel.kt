package br.com.amedigital.lodjinha.feature.category.gateway

import android.app.Application
import android.util.Log
import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.business.dto.Products
import br.com.amedigital.lodjinha.base.business.dto.QueryParam
import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.gateway.BaseViewModel
import br.com.amedigital.lodjinha.feature.home.business.Banners
import br.com.amedigital.lodjinha.feature.home.business.Categories
import br.com.amedigital.lodjinha.feature.home.gateway.HomeViewModel
import br.com.amedigital.lodjinha.plugin.di.injector

class CategoryViewModel(application: Application): BaseViewModel(application) {
    companion object {
        const val CATEGORIES_CHANNEL = "categories"
    }

    private val productsByCategoryUseCase by lazy { injector().productsByCategoryUseCase }
    private var executing: Boolean = false
    private lateinit var currentQuery: QueryParam
    private lateinit var lastPage: Pageable<*>

    val availableChannels = listOf(CATEGORIES_CHANNEL)

    fun getProducts(categoryId: Long) {
        if(!executing) {
            fetch(categoryId)
        }
    }

    private fun fetch(categoryId: Long) {
        if(!::lastPage.isInitialized) {
            firstPage(categoryId)
        } else {
            nextPage()
        }
    }

    private fun firstPage(categoryId: Long) {
        Log.w("PRODUTOS", "firstPage called")

        currentQuery = QueryParam(categoryId)
        request()
    }

    private fun nextPage() {
        Log.w("PRODUTOS", "nextPage called")

        val (_, total, offset) = lastPage
        val nextOffset = offset + currentQuery.limit
        if(nextOffset < total) {
            currentQuery = currentQuery.copy(offset = nextOffset)
            request()
        }
    }

    private fun request() {
        executing = true
        request(CATEGORIES_CHANNEL, productsByCategoryUseCase, currentQuery)
    }

    override fun postValue(channelName: String, output: Output<*>) {
        executing = false
        if(output.value is Pageable<*>) lastPage = output.value
        super.postValue(channelName, output)
    }
}
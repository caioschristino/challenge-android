package br.com.amedigital.lodjinha.feature.product.gateway

import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.business.dto.QueryParam
import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.gateway.BaseViewModel
import br.com.amedigital.lodjinha.feature.product.di.CategoryInjector

class CategoryViewModel: BaseViewModel() {
    companion object {
        const val CATEGORIES_CHANNEL = "categories"
    }
    private val productsByCategoryUseCase by lazy { CategoryInjector.injector.productsByCategoryUseCase }

    private var executing: Boolean = false
    private lateinit var currentQuery: QueryParam
    private lateinit var lastPage: Pageable<*>

    override fun declareChannels() {
        availableChannels.add(CATEGORIES_CHANNEL)
    }

    fun getProducts(categoryId: Long) {
        if(!executing) {
            currentQuery = QueryParam(categoryId)
            request()
        }
    }

    fun getMoreProducts() {
        if(!executing) {
            val (_, total, offset) = lastPage
            val nextOffset = offset + currentQuery.limit
            if(nextOffset < total) {
                currentQuery = currentQuery.copy(offset = nextOffset)
                request()
            }
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
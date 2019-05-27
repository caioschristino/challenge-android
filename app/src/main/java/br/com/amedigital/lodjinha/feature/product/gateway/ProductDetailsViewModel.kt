package br.com.amedigital.lodjinha.feature.product.gateway

import br.com.amedigital.lodjinha.base.gateway.BaseViewModel
import br.com.amedigital.lodjinha.feature.product.di.ProductDetailsInjector

class ProductDetailsViewModel: BaseViewModel() {
    companion object {
        const val DETAILS_CHANNEL = "proddetails"
    }
    private val productDetailsUseCase by lazy { ProductDetailsInjector.injector.productDetailsUseCase }

    override fun declareChannels() {
        availableChannels.add(DETAILS_CHANNEL)
    }

    fun getProduct(prodId: Long) {
        request(DETAILS_CHANNEL, productDetailsUseCase, prodId)
    }
}
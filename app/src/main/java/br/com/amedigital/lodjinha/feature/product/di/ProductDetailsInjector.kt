package br.com.amedigital.lodjinha.feature.product.di

import br.com.amedigital.lodjinha.feature.product.business.GetProductDetailsUseCase

interface ProductDetailsInjector {
    companion object {
        lateinit var injector: ProductDetailsInjector
    }

    val productDetailsUseCase: GetProductDetailsUseCase
}
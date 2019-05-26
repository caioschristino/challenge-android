package br.com.amedigital.lodjinha.feature.product.di

import br.com.amedigital.lodjinha.feature.product.business.GetProductsByCategoryUseCase

interface CategoryInjector {

    companion object {
        lateinit var injector: CategoryInjector
    }

    val productsByCategoryUseCase: GetProductsByCategoryUseCase
}
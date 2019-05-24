package br.com.amedigital.lodjinha.feature.category.di

import br.com.amedigital.lodjinha.feature.category.business.GetProductsByCategoryUseCase

interface CategoryInjector {
    val productsByCategoryUseCase: GetProductsByCategoryUseCase
}
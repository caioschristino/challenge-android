package br.com.amedigital.lodjinha.plugin.di

import br.com.amedigital.lodjinha.feature.category.business.CategoryRepository
import br.com.amedigital.lodjinha.feature.category.business.GetProductsByCategoryUseCase
import br.com.amedigital.lodjinha.feature.category.di.CategoryInjector
import br.com.amedigital.lodjinha.feature.category.gateway.CategoryViewModel
import br.com.amedigital.lodjinha.feature.home.business.GetBannersUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetCategoriesUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetSalesRankUseCase
import br.com.amedigital.lodjinha.feature.home.business.HomeRepository
import br.com.amedigital.lodjinha.feature.home.di.HomeInjector
import br.com.amedigital.lodjinha.feature.home.gateway.HomeViewModel
import br.com.amedigital.lodjinha.plugin.repository.CategoryRepositoryImpl
import br.com.amedigital.lodjinha.plugin.repository.HomeRepositoryImpl

val homeRepository: HomeRepository by lazy { HomeRepositoryImpl() }
val categoryRepository: CategoryRepository by lazy { CategoryRepositoryImpl() }

fun HomeViewModel.Companion.injector() = object: HomeInjector {
    override val bannersUseCase: GetBannersUseCase get() = GetBannersUseCase(homeRepository)
    override val categoriesUseCase: GetCategoriesUseCase get() = GetCategoriesUseCase(homeRepository)
    override val salesRankUseCase: GetSalesRankUseCase get() = GetSalesRankUseCase(homeRepository)
}

fun CategoryViewModel.Companion.injector() = object: CategoryInjector {
    override val productsByCategoryUseCase: GetProductsByCategoryUseCase
        get() = GetProductsByCategoryUseCase(categoryRepository)

}
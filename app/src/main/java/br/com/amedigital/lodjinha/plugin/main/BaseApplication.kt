package br.com.amedigital.lodjinha.plugin.main

import android.app.Application
import br.com.amedigital.lodjinha.feature.home.business.GetBannersUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetCategoriesUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetSalesRankUseCase
import br.com.amedigital.lodjinha.feature.home.business.HomeRepository
import br.com.amedigital.lodjinha.feature.home.di.HomeInjector
import br.com.amedigital.lodjinha.feature.product.business.CategoryRepository
import br.com.amedigital.lodjinha.feature.product.business.GetProductDetailsUseCase
import br.com.amedigital.lodjinha.feature.product.business.GetProductsByCategoryUseCase
import br.com.amedigital.lodjinha.feature.product.di.CategoryInjector
import br.com.amedigital.lodjinha.feature.product.di.ProductDetailsInjector
import br.com.amedigital.lodjinha.plugin.repository.CategoryRepositoryImpl
import br.com.amedigital.lodjinha.plugin.repository.HomeRepositoryImpl
import br.com.amedigital.lodjinha.plugin.repository.ProductDetailsRepositoryImpl

class BaseApplication : Application() {

    private val homeRepository by lazy { HomeRepositoryImpl() }
    private val categoryRepository by lazy { CategoryRepositoryImpl() }
    private val productDetailsRepository by lazy { ProductDetailsRepositoryImpl() }

    override fun onCreate() {
        super.onCreate()
        injectHome()
        injectCategory()
        injectProductDetails()
    }

    private fun injectProductDetails() {
        ProductDetailsInjector.injector = object : ProductDetailsInjector {
            override val productDetailsUseCase: GetProductDetailsUseCase
                get() = GetProductDetailsUseCase(productDetailsRepository)
        }
    }

    private fun injectCategory() {
        CategoryInjector.injector = object : CategoryInjector {
            override val productsByCategoryUseCase: GetProductsByCategoryUseCase
                get() = GetProductsByCategoryUseCase(categoryRepository)
        }
    }

    private fun injectHome() {
        HomeInjector.injector = object : HomeInjector {
            override val bannersUseCase: GetBannersUseCase get() = GetBannersUseCase(homeRepository)
            override val categoriesUseCase: GetCategoriesUseCase get() = GetCategoriesUseCase(homeRepository)
            override val salesRankUseCase: GetSalesRankUseCase get() = GetSalesRankUseCase(homeRepository)
        }
    }
}
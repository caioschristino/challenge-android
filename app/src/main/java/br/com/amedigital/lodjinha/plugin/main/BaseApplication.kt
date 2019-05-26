package br.com.amedigital.lodjinha.plugin.main

import android.app.Application
import br.com.amedigital.lodjinha.feature.home.business.GetBannersUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetCategoriesUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetSalesRankUseCase
import br.com.amedigital.lodjinha.feature.home.business.HomeRepository
import br.com.amedigital.lodjinha.feature.home.di.HomeInjector
import br.com.amedigital.lodjinha.feature.product.business.CategoryRepository
import br.com.amedigital.lodjinha.feature.product.business.GetProductsByCategoryUseCase
import br.com.amedigital.lodjinha.feature.product.di.CategoryInjector
import br.com.amedigital.lodjinha.plugin.repository.CategoryRepositoryImpl
import br.com.amedigital.lodjinha.plugin.repository.HomeRepositoryImpl

class BaseApplication : Application() {

    val homeRepository: HomeRepository by lazy { HomeRepositoryImpl() }
    val categoryRepository: CategoryRepository by lazy { CategoryRepositoryImpl() }

    override fun onCreate() {
        super.onCreate()
        inject()
    }

    fun inject() {
        HomeInjector.injector = object: HomeInjector {
            override val bannersUseCase: GetBannersUseCase get() = GetBannersUseCase(homeRepository)
            override val categoriesUseCase: GetCategoriesUseCase get() = GetCategoriesUseCase(homeRepository)
            override val salesRankUseCase: GetSalesRankUseCase get() = GetSalesRankUseCase(homeRepository)
        }

        CategoryInjector.injector = object: CategoryInjector {
            override val productsByCategoryUseCase: GetProductsByCategoryUseCase
                get() = GetProductsByCategoryUseCase(categoryRepository)
        }
    }
}
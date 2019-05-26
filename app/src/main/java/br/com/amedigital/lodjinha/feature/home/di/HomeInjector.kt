package br.com.amedigital.lodjinha.feature.home.di

import br.com.amedigital.lodjinha.feature.home.business.GetBannersUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetCategoriesUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetSalesRankUseCase

interface HomeInjector {

    companion object {
        lateinit var injector: HomeInjector
    }

    val bannersUseCase: GetBannersUseCase
    val categoriesUseCase: GetCategoriesUseCase
    val salesRankUseCase: GetSalesRankUseCase
}
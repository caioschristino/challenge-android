package br.com.amedigital.lodjinha.feature.home.di

import br.com.amedigital.lodjinha.feature.home.business.GetBannersUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetCategoriesUseCase
import br.com.amedigital.lodjinha.feature.home.business.GetSalesRankUseCase

interface HomeInjector {
    val bannersUseCase: GetBannersUseCase
    val categoriesUseCase: GetCategoriesUseCase
    val salesRankUseCase: GetSalesRankUseCase
}
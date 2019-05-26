package br.com.amedigital.lodjinha.feature.home.gateway

import br.com.amedigital.lodjinha.base.gateway.BaseViewModel
import br.com.amedigital.lodjinha.feature.home.di.HomeInjector

class HomeViewModel: BaseViewModel() {
    companion object {
        const val BANNERS_CHANNEL = "banners"
        const val CATEGORIES_CHANNEL = "categories"
        const val RANK_CHANNEL = "rank"
    }

    private val bannersUseCase by lazy { HomeInjector.injector.bannersUseCase }
    private val categoriesUseCase by lazy { HomeInjector.injector.categoriesUseCase }
    private val salesRankUseCase by lazy { HomeInjector.injector.salesRankUseCase }

    override fun declareChannels() {
        availableChannels.addAll(listOf(BANNERS_CHANNEL, CATEGORIES_CHANNEL, RANK_CHANNEL))
    }

    fun getBanners() {
        request(channelName = BANNERS_CHANNEL, useCase = bannersUseCase)
    }

    fun getCategories() {
        request(channelName = CATEGORIES_CHANNEL, useCase = categoriesUseCase)
    }

    fun getSalesRank() {
        request(channelName = RANK_CHANNEL, useCase = salesRankUseCase)
    }
}
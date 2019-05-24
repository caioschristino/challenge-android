package br.com.amedigital.lodjinha.plugin.repository

import br.com.amedigital.lodjinha.feature.home.business.HomeRepository
import br.com.amedigital.lodjinha.model.Banner
import br.com.amedigital.lodjinha.model.Categoria
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.plugin.network.getAPI

internal class HomeRepositoryImpl: BaseRepository(), HomeRepository {

    override val banners: List<Banner>?
        get() = cacheOrApi("banners") { getAPI().getBanners().execute().body()?.data}

    override val categories: List<Categoria>?
        get() = cacheOrApi("categories") { getAPI().getCategories().execute().body()?.data}

    override val salesRank: List<Produto>?
        get() = cacheOrApi("rank") { getAPI().getProdSalesRank().execute().body()?.data}
}
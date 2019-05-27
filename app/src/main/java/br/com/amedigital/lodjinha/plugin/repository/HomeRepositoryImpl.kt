package br.com.amedigital.lodjinha.plugin.repository

import android.os.AsyncTask.execute
import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.business.exception.InternetConnectionException
import br.com.amedigital.lodjinha.feature.home.business.HomeRepository
import br.com.amedigital.lodjinha.model.Banner
import br.com.amedigital.lodjinha.model.Categoria
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.plugin.network.executeCall
import br.com.amedigital.lodjinha.plugin.network.getAPI
import retrofit2.Call

internal class HomeRepositoryImpl: BaseRepository(), HomeRepository {

    override val banners: List<Banner>?
        get() = cacheOrApi("banners") {
            val call = getAPI().getBanners()
            executeCall(call)?.body()?.data
        }

    override val categories: List<Categoria>?
        get() = cacheOrApi("categories") {
            val call = getAPI().getCategories()
            executeCall(call)?.body()?.data
        }

    override val salesRank: List<Produto>?
        get() = cacheOrApi("rank") {
            val call = getAPI().getProdSalesRank()
            executeCall(call)?.body()?.data
        }
}
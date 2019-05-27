package br.com.amedigital.lodjinha.plugin.repository

import android.util.Log
import br.com.amedigital.lodjinha.feature.product.business.ProductDetailsRepository
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.plugin.network.getAPI

class ProductDetailsRepositoryImpl: BaseRepository(), ProductDetailsRepository {

    override fun getProduct(prodId: Long): Produto? {
        Log.w("DETAILS", "api called")

        return getAPI().getProdById(prodId).execute().body()
    }
}
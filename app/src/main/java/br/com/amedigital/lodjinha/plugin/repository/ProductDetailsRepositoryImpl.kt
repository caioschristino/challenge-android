package br.com.amedigital.lodjinha.plugin.repository

import br.com.amedigital.lodjinha.feature.product.business.ProductDetailsRepository
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.plugin.network.executeCall
import br.com.amedigital.lodjinha.plugin.network.getAPI

class ProductDetailsRepositoryImpl: BaseRepository(), ProductDetailsRepository {

    override fun getProduct(prodId: Long): Produto? {
        val call = getAPI().getProdById(prodId)
        return executeCall(call)?.body()
    }
}
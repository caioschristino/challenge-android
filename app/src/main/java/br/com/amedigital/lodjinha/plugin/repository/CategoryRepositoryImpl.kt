package br.com.amedigital.lodjinha.plugin.repository

import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.business.exception.HttpException
import br.com.amedigital.lodjinha.feature.product.business.CategoryRepository
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.plugin.network.executeCall
import br.com.amedigital.lodjinha.plugin.network.getAPI

internal class CategoryRepositoryImpl: BaseRepository(), CategoryRepository {
    override fun getProducts(categoryId: Long, offset: Int, limit: Int): Pageable<Produto>? {
        val call =  getAPI().getProdByCategory(categoryId, offset, limit)
        return executeCall(call)?.body()
    }
}

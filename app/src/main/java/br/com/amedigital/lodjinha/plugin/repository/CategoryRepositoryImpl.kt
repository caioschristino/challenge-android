package br.com.amedigital.lodjinha.plugin.repository

import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.feature.category.business.CategoryRepository
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.plugin.network.getAPI

internal class CategoryRepositoryImpl: BaseRepository(), CategoryRepository {
    override fun getProducts(categoryId: Long, offset: Int, limit: Int): Pageable<Produto>? {
        return getAPI()
            .getProdByCategory(categoryId, offset, limit)
            .execute()
            .body()
    }
}

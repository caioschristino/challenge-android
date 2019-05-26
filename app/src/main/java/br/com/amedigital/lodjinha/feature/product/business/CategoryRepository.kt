package br.com.amedigital.lodjinha.feature.product.business

import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.model.Produto

interface CategoryRepository {
    fun getProducts(categoryId: Long, offset: Int, limit: Int): Pageable<Produto>?
}
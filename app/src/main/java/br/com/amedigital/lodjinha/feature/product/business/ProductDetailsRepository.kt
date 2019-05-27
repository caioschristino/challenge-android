package br.com.amedigital.lodjinha.feature.product.business

import br.com.amedigital.lodjinha.model.Produto

interface ProductDetailsRepository {
    fun getProduct(prodId: Long): Produto?
}
package br.com.amedigital.lodjinha.base.business.dto

import br.com.amedigital.lodjinha.model.Produto
import java.io.Serializable

data class Products(val products: List<Produto>?)

data class QueryParam(val id: Long, val offset: Int = 0, val limit: Int = 15)

data class Pageable<out V>(val data: List<V>, val total: Int = data.size, val offset: Int = 0): Serializable
package br.com.amedigital.lodjinha.feature.home.business

import br.com.amedigital.lodjinha.model.Banner
import br.com.amedigital.lodjinha.model.Categoria
import br.com.amedigital.lodjinha.model.Produto

interface HomeRepository {
    val banners: List<Banner>?
    val categories: List<Categoria>?
    val salesRank: List<Produto>?
}
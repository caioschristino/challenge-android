package br.com.amedigital.lodjinha.feature.home.business

import br.com.amedigital.lodjinha.base.business.dto.Products
import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.business.interactor.UseCase

class GetSalesRankUseCase(private val repository: HomeRepository): UseCase<Nothing, Products>() {
    override fun execute(param: Nothing?): Output<Products> {
        return Output.success(Products(repository.salesRank ?: emptyList()))
    }
}
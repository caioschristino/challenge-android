package br.com.amedigital.lodjinha.feature.product.business

import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.business.interactor.UseCase
import br.com.amedigital.lodjinha.model.Produto

class GetProductDetailsUseCase(private val repository: ProductDetailsRepository): UseCase<Long, Produto>() {

    override fun guard(param: Long?): Boolean {
        return param != null
    }

    override fun execute(param: Long?): Output<Produto> {
        return Output.success(repository.getProduct(param!!))
    }
}
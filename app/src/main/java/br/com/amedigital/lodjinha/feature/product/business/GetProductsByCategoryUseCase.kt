package br.com.amedigital.lodjinha.feature.product.business

import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.business.dto.QueryParam
import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.business.interactor.UseCase
import br.com.amedigital.lodjinha.model.Produto

class GetProductsByCategoryUseCase(private val repository: CategoryRepository):
    UseCase<QueryParam, Pageable<Produto>>() {

    override fun guard(param: QueryParam?): Boolean {
        return param != null
    }

    override fun execute(param: QueryParam?): Output<Pageable<Produto>> {
        val (categoryId, offset, limit) = param!!
        val products = repository.getProducts(categoryId, offset, limit)
        return Output.success(products)
    }
}
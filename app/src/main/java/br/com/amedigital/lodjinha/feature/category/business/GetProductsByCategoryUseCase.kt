package br.com.amedigital.lodjinha.feature.category.business

import android.util.Log
import androidx.core.graphics.component1
import androidx.core.graphics.component2
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
        Log.w("PRODUTOS", "${javaClass.simpleName} called")

        val (categoryId, offset, limit) = param!!
        val products = repository.getProducts(categoryId, offset, limit)
        return Output.success(products)
    }
}
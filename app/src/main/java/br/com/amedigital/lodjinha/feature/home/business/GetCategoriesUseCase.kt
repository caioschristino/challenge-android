package br.com.amedigital.lodjinha.feature.home.business

import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.business.interactor.UseCase
import br.com.amedigital.lodjinha.model.Categoria

data class Categories(val categories: List<Categoria>?)

class GetCategoriesUseCase (private val repository: HomeRepository): UseCase<Nothing, Categories>() {
    override fun execute(param: Nothing?): Output<Categories> {
        return Output.success(Categories(repository.categories))
    }
}
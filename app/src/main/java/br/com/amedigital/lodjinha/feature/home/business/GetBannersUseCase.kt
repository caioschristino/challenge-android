package br.com.amedigital.lodjinha.feature.home.business

import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.business.interactor.UseCase
import br.com.amedigital.lodjinha.model.Banner

data class Banners(val banners: List<Banner>?)

class GetBannersUseCase(private val repository: HomeRepository): UseCase<Nothing, Banners>() {
    override fun execute(param: Nothing?): Output<Banners> {
        return Output.success(Banners(repository.banners))
    }
}
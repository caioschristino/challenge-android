package br.com.amedigital.lodjinha.feature.category.gateway

import android.app.Application
import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.business.dto.QueryParam
import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.gateway.BaseViewModel
import br.com.amedigital.lodjinha.plugin.di.injector

class CategoryViewModel(application: Application): BaseViewModel(application) {
    companion object;

    private val productsByCategoryUseCase by lazy { injector().productsByCategoryUseCase }
    private lateinit var lastPage: Pageable<*>

    fun getProducts(categoryId: Long) {
        request(useCase = productsByCategoryUseCase, param = QueryParam(categoryId))
    }

    override fun postValue(channelName: String?, output: Output<*>) {
        if(output.value is Pageable<*>) lastPage = output.value
        super.postValue(channelName, output)
    }
}
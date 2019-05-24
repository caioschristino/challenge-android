package br.com.amedigital.lodjinha.feature.home.gateway

import br.com.amedigital.lodjinha.feature.home.business.GetBannersUseCase
import br.com.amedigital.lodjinha.plugin.di.injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private val mainThread = newSingleThreadContext("UI thread")
    private val bannersUseCase: GetBannersUseCase by lazy { HomeViewModel.injector().bannersUseCase }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThread)
    }
}
package br.com.amedigital.lodjinha.base.gateway

/**
 * @author Alessandro Balotta de Oliveira
 *
 * This file has the essential components of the Gateway Layer: ViewState and BaseViewModel
 *
 * ViewState: is a DTO for view states and for the SingleLiveEvent principle
 * BaseViewModel: AndroidViewModel that holds a Map of Channels and bridges the
 * business layer and the view layer
 *
 */

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import br.com.amedigital.lodjinha.base.business.interactor.Output
import br.com.amedigital.lodjinha.base.business.interactor.UseCase


open class ViewState(val output: Output<*>, var handled: Boolean = false) {
    fun isError(): Boolean = output.isError()
    fun isEmpty(): Boolean = output.isEmpty()
    fun isSuccess(): Boolean = output.isSuccess()
}

private data class Channel(val stream: MutableLiveData<ViewState>)

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {
    private val channels: MutableMap<String, Channel> = mutableMapOf()
    private val liveData: MediatorLiveData<ViewState> = MediatorLiveData()

    fun observe(channelName: String, owner: LifecycleOwner, listener: Observer<in ViewState>) {
        createChannelIfNeeded(channelName)
        channels[channelName]!!.stream.observe(owner, listener)
    }

    fun observe(owner: LifecycleOwner, listener: Observer<in ViewState>) {
        liveData.observe(owner, listener)
    }

    protected open fun <P,R> request(channelName: String? = null, useCase: UseCase<P, R>, param: P? = null) {
        UseCase.invoker(useCase).dispatch(param) {postValue(channelName, it)}
    }

    protected open fun postValue(channelName: String? = null, output: Output<*>) {
        val channel = channels[channelName]
        val viewState = ViewState(output)
        channel?.stream?.postValue(viewState) ?: liveData.postValue(viewState)
    }

    private fun createChannelIfNeeded(channelName: String) {
        if(channels[channelName] == null) {
            channels[channelName] = Channel(MutableLiveData())
            liveData.addSource(channels[channelName]!!.stream) {liveData.value = it}
        }
    }
}

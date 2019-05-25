package br.com.amedigital.lodjinha.base.gateway

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

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {
    private val channels: MutableMap<String, MutableLiveData<ViewState>> = mutableMapOf()

    fun observe(channelName: String, owner: LifecycleOwner, listener: Observer<in ViewState>) {
        createChannelIfNeeded(channelName)
        channels[channelName]!!.observe(owner, listener)
    }

    protected open fun <P,R> request(channelName: String, useCase: UseCase<P, R>, param: P? = null) {
        useCase.invoke(param) {postValue(channelName, it)}
    }

    protected open fun postValue(channelName: String, output: Output<*>) {
        Log.w("BASE", "postValue called")

        val channel = channels[channelName]
        val viewState = ViewState(output)
        channel?.postValue(viewState)
    }

    private fun createChannelIfNeeded(channelName: String) {
        if(channels[channelName] == null) {
            channels[channelName] = MutableLiveData()
        }
    }
}

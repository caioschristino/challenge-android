package br.com.amedigital.lodjinha.base.view.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.business.exception.HttpException
import br.com.amedigital.lodjinha.base.business.exception.InternetConnectionException
import br.com.amedigital.lodjinha.base.gateway.BaseViewModel
import br.com.amedigital.lodjinha.base.gateway.ViewState

data class DialogConfig(
    val message: String,
    val okTxt: Int = R.string.ok,
    val noTxt: Int = 0,
    val okListener: (DialogInterface, Int) -> Unit = { d,_ -> d.dismiss()},
    val noListener: (DialogInterface, Int) -> Unit = { d,_ -> d.dismiss()})

abstract class BaseFragment<V:BaseViewModel>: AbstractFragment() {
    protected lateinit var viewModel: V
    private var connectionDialog: AlertDialog? = null
    private var loading: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews(view)
        observeViewModel()
    }

    @CallSuper
    override fun setupViews(view: View) {
        super.setupViews(view)
        loading = view.findViewById(R.id.loading)
    }

    protected open fun showLoading() {
        loading?.visibility = View.VISIBLE
    }

    protected open fun hideLoading() {
        try {
            loading?.visibility = View.GONE
        } catch(e: Exception) {
            Log.e("ERROR", "error loading", e)
        }
    }

    protected open fun observeViewModel() {
        observeAllChannels()
    }

    protected fun observeAllChannels() {
        viewModel.getChannels().forEach { observeChannel(it) }
    }

    protected fun observeChannel(channelName: String) {
        viewModel.observe(channelName,this, Observer { v-> handleResponse(v) })
    }

    protected fun setupViewModel() {
        val clazz = getViewModelClass()
        viewModel = ViewModelProviders.of(this).get(clazz)
    }

    protected abstract fun getViewModelClass(): Class<V>

    protected open fun handleResponse(state: ViewState) {
        if(!state.handled)  {
            hideLoading()
            state.handled = true
            if (state.isError()) {
                handleThrowable(state.output.error)
            } else {
                handleSuccess(state.output.value)
            }
        }
    }

    private fun handleThrowable(error: Throwable?) {
        when(error) {
            is HttpException -> handleHttpError(error)
            is InternetConnectionException -> handleConnectionError()
            else -> handleError(error)
        }
    }

    protected open fun handleHttpError(error: HttpException) {
        Toast.makeText(context, "Algo deu errado! Erro HTTP: ${error.code}", Toast.LENGTH_LONG).show()
    }

    protected open fun handleConnectionError() {
        if(connectionDialog == null || !connectionDialog!!.isShowing) {
            connectionDialog = createConnectionDialog()
            connectionDialog?.show()
        }
    }

    protected open fun createConnectionDialog(): AlertDialog? {
        return getDialog(DialogConfig(
            message = getString(R.string.connection_unavailable),
            okListener = ::navigateToConnectivity
        ))
    }

    protected open fun navigateToConnectivity(dialog: DialogInterface, which: Int) {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_connectivityFragment)
    }

    protected open fun getDialog(config: DialogConfig): AlertDialog? {
        return  AlertDialog.Builder(context).let {
            it.setMessage(config.message)
            it.setPositiveButton(config.okTxt, config.okListener)
            if(config.noTxt != 0) it.setNegativeButton(config.noTxt, config.noListener)
            it.create()
        }
    }

    protected open fun handleError(error: Throwable?) {}

    protected open fun handleSuccess(value: Any?) {}

    protected fun reveal(view: View) {
        if(view.visibility != View.VISIBLE) {
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            view.visibility = View.VISIBLE
        }
    }
}
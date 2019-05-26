package br.com.amedigital.lodjinha.base.business.interactor

/**
 * @author Alessandro Balotta de Oliveira
 *
 * An UseCaseInvoker can run AbstractUseCase on coroutines
 * Interactor's methods are executed on the `executeOn` dispatcher
 * AbstractUseCase's outputting methods are executed on the `resultOn` dispatcher
 */

import kotlinx.coroutines.*

open class UseCaseInvoker<in P,R>(
    private val useCase: AbstractUseCase<P, R>,
    private val executeOn: CoroutineDispatcher = Dispatchers.IO,
    private val resultOn: CoroutineDispatcher = Dispatchers.Main
): Interactor<P, R> by useCase {

    fun dispatch(param: P? = null, callback: (R)->Unit): Job? {
        useCase.callback = callback
        onBeforeDispatch()
        return GlobalScope.launch(executeOn) {
            onDispatch(param)
        }
    }

    protected open fun onBeforeDispatch() {}

    private suspend fun onDispatch(param: P? = null) {
        try {
            onBeforeExecute()
            onExecute(param)
        } catch(error: Throwable) {
            onError(error)
        }
    }

    protected open fun onBeforeExecute() {}

    private suspend fun onExecute(param: P? = null) {
        if (useCase.guard(param)) {
            onSuccess(useCase.execute(param))
        }
    }

    private suspend fun onSuccess(output: R) {
        withContext(resultOn) {
            try {
                onBeforeSuccess()
                useCase.onSuccess(output)
                onFinish()
            } catch(error: Throwable) {
                onError(error)
            }
        }
    }

    protected open fun onBeforeSuccess() {}

    private suspend fun onError(error: Throwable) {
        withContext(resultOn) {
            onBeforeError();
            useCase.onError(error)
            onFinish()
        }
    }

    protected open fun onBeforeError() {}

    protected open fun onFinish() {}
}
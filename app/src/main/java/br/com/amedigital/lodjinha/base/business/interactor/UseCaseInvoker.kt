package br.com.amedigital.lodjinha.base.business.interactor

/**
 * @author Alessandro Balotta de Oliveira
 *
 * An UseCaseInvoker can run AbstractUseCase on coroutines
 * Interactor's methods are executed on the `executeOn` coroutine
 * AbstractUseCase's outputting methods are executed on the `resultOn` coroutine
 * It has three steps:
 * - dispatch: before entering `executeOn`,
 * - launch: `executeOn` launched and is running
 * - finish: `resultOn` launched and is running
 * Also, it implements the Template Method Pattern by providing a hook method for each step
 *
 */

import kotlinx.coroutines.*

open class UseCaseInvoker<in P,R>(
    private val useCase: AbstractUseCase<P, R>,
    private val executeOn: CoroutineDispatcher = Dispatchers.IO,
    private val resultOn: CoroutineDispatcher = Dispatchers.Main
): Interactor<P, R> by useCase {

    fun dispatch(param: P? = null, callback: (R)->Unit): Job? {
        useCase.callback = callback
        onDispatch()
        return GlobalScope.launch(executeOn) {
            try {
                onLaunch()
                if (useCase.guard(param)) {
                    val result = useCase.execute(param)
                    onSuccess(result)
                }
            } catch(error: Throwable) {
                onError(error)
            }
        }
    }

    protected open fun onLaunch() {}

    protected open fun onDispatch() {}

    protected open fun onFinish() {}

    private suspend fun onSuccess(output: R) {
        withContext(resultOn) {
            try {
                onFinish()
                useCase.onSuccess(output)
            } catch(error: Throwable) {
                onError(error)
            }
        }
    }

    private suspend fun onError(error: Throwable) {
        withContext(resultOn) {
            onFinish()
            useCase.onError(error)
        }
    }
}
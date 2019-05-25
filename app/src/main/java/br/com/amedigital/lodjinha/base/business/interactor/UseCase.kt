package br.com.amedigital.lodjinha.base.business.interactor

import kotlinx.coroutines.Job

/**
 * @author Alessandro Balotta de Oliveira
 *
 * This file has the main components of the Business Layer: AbstractUseCase and UseCase
 *
 * An Use Case is a guarded executable unit that receives a nullable param and
 * outputs a non-null result or Nothing to a callback lambda.
 * Use Cases may be executed on a separate Thread but they don't enforce it.
 * They automatically catch any exceptions and may wrap them in the output.
 *
 * AbstractUseCase: the Use Case all others inherit from. It implements the Template Method Pattern
 * UseCase: the Use Case that outputs either a nullable value or an error
 *
 */

abstract class AbstractUseCase<in P, R>: Interactor<P, R> {
    lateinit var callback: (R)->Unit

    fun process(param: P? = null, callback: (R)->Unit) {
        try {
            this.callback = callback
            handle(param)
        } catch(error: Throwable) {
            onError(error)
        }
    }

    protected open fun handle(param: P?) {
        takeIf { guard(param) }?.execute(param).also { onSuccess(it!!) }
    }

    internal open fun onSuccess(output: R) {
        callback(output)
    }

    internal abstract fun onError(error: Throwable)
}


abstract class UseCase<in P, R>: AbstractUseCase<P, Output<R>>() {
    override fun onError(error: Throwable) {
        callback(Output.failure(error))
    }

    fun invoke(param: P? = null, callback: (Output<R>)->Unit): Job? {
        return UseCaseInvoker(this).dispatch(param, callback)
    }
}
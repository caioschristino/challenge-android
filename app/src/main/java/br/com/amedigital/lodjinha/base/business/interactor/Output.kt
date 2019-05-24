package br.com.amedigital.lodjinha.base.business.interactor

class Output<V> private constructor(val value: V?, val error: Throwable?) {
    companion object {
        fun <V> success(value: V? = null): Output<V> = Output(value, null)
        fun <V> failure(throwable: Throwable): Output<V> = Output(null, throwable)
    }

    fun isError(): Boolean = error != null
    fun isEmpty(): Boolean = value == null
    fun isSuccess(): Boolean = !isError()
}
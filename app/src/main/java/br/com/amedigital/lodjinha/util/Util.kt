package br.com.amedigital.lodjinha.util

import android.os.Build

fun isLollipopOrHigher() : Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

fun currencyBRL(value: Double): String {
    return "R$ ${String.format("%.2f", value).replace(".", ",")}"
}
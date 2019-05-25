package br.com.amedigital.lodjinha.util

import android.os.Build
import android.text.Html
import android.text.Spanned

fun isLollipopOrHigher() : Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

fun currencyBRL(value: Double): String {
    return "R$ ${String.format("%.2f", value).replace(".", ",")}"
}

fun parseHTML(value: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(value)
    }
}
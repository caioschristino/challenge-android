package br.com.amedigital.lodjinha.base.view.util

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.ImageView
import br.com.amedigital.lodjinha.R
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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

fun loadImageIntoView(itemView: View, view: ImageView, url: String) {
    Glide
        .with(itemView)
        .load(url)
        .transition(GenericTransitionOptions.with(R.anim.zoom_in))
        .apply(
            RequestOptions()
                .error(R.drawable.ic_highlight_off)
                .centerCrop())
        .into(view)
}
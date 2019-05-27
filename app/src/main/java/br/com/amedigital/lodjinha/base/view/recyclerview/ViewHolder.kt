package br.com.amedigital.lodjinha.base.view.recyclerview

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.util.loadImageIntoView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

open class ViewHolder<P>(itemView: View, val bindingEnabled: Boolean = true): RecyclerView.ViewHolder(itemView) {

    open fun bind(item: P, listener: (P) -> Unit) = with(itemView) {
        if(bindingEnabled) setOnClickListener { listener.invoke(item) }
    }

    open fun animateText(txt: TextView) {
        txt.startAnimation(AnimationUtils.loadAnimation(txt.context, R.anim.fade_in))
    }

    open fun getString(@StringRes stringId: Int, arg: Any): String {
        return itemView.context.getString(stringId, arg)
    }

    open fun loadImage(url: String, view: ImageView) = loadImageIntoView(itemView, view, url)

}

class ProgressViewHolder<P>(itemView: View) : ViewHolder<P>(itemView, false)
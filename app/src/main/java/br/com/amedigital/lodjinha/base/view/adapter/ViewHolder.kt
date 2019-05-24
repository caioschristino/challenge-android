package br.com.amedigital.lodjinha.base.view.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.model.Categoria
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

open class ViewHolder<P>(itemView: View): RecyclerView.ViewHolder(itemView) {
    open fun bind(item: P?, listener: (P?) -> Unit) = with(itemView) {
        setOnClickListener { listener.invoke(item) }
    }

    open fun animateText(txt: TextView) {
        txt.startAnimation(AnimationUtils.loadAnimation(txt.context, R.anim.fade_in))
    }

    open fun getString(@StringRes stringId: Int, arg: Any): String {
        return itemView.context.getString(stringId, arg)
    }

    open fun loadImage(url: String, view: ImageView) =
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

class Listener: RequestListener<Drawable> {
    override fun onLoadFailed(e: GlideException?, model: Any?,
                              target: Target<Drawable>?, isFirstResource: Boolean
    ): Boolean {
        Log.e("CATEGORIA", "load failed", e)
        return false
    }

    override fun onResourceReady(resource: Drawable?, model: Any?,
                                 target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
    ): Boolean {
        return false
    }
}
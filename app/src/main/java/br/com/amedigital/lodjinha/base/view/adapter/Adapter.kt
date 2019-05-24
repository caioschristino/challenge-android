package br.com.amedigital.lodjinha.base.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class Adapter<P>(@LayoutRes val layoutId: Int, val items: List<P>?, val listener: (P?)->Unit): RecyclerView.Adapter<ViewHolder<P>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<P> {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return getViewHolder(view)
    }

    protected open fun getViewHolder(view: View): ViewHolder<P> {
        return ViewHolder(view)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder<P>, position: Int) = holder.bind(items?.get(position), listener)
}
package br.com.amedigital.lodjinha.base.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class Adapter<P>(
    @LayoutRes val layoutId: Int,
    items: List<P> = emptyList(),
    private val listener: (P)->Unit): RecyclerView.Adapter<ViewHolder<P>>() {

    private val list: MutableList<P> = mutableListOf()

    init {
        list.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<P> {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return getViewHolder(view)
    }

    fun add(value: P) {
        list.add(value)
        notifyDataSetChanged()
    }

    fun addAll(values: List<P>) {
        list.addAll(values)
        notifyDataSetChanged()
    }

    protected open fun getViewHolder(view: View): ViewHolder<P> {
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder<P>, position: Int) = holder.bind(list[position], listener)
}
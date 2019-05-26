package br.com.amedigital.lodjinha.base.view.recyclerview

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections.addAll

abstract class Adapter<P>(items: List<P> = emptyList(), protected val listener: (P)->Unit): RecyclerView.Adapter<ViewHolder<P>>() {
    protected val list: MutableList<P?> = mutableListOf()

    init {
        addAll(items)
    }

    protected abstract fun getFactoryMediator(): ViewHolderFactoryMediator<P>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<P> {
        return getFactoryMediator().getViewHolderFactory(viewType).create(parent)
    }

    open fun add(value: P?) {
        list.add(value)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(values: List<P>) {
        list.addAll(values)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return getFactoryMediator().getViewType(position, list)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder<P>, position: Int) {
        if(list[position] != null) holder.bind(list[position]!!, listener)
    }
}
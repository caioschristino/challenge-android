package br.com.amedigital.lodjinha.base.view.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager


class InfiniteScrollListener(private val listener: ()->Unit) : RecyclerView.OnScrollListener() {
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if(dx == 0 && dy == 0) return

        val manager = recyclerView.layoutManager as LinearLayoutManager
        val totalItems = recyclerView.adapter!!.itemCount
        val lastVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition()

        if (!isLoading && totalItems - 1 == lastVisibleItemPosition) {
            listener()
            isLoading = true
        }
    }


    fun setLoaded() {
        isLoading = false
    }
}
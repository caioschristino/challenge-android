package br.com.amedigital.lodjinha.feature.category.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.view.adapter.ProductAdapter
import br.com.amedigital.lodjinha.base.view.ui.BaseFragment
import br.com.amedigital.lodjinha.feature.category.gateway.CategoryViewModel
import br.com.amedigital.lodjinha.model.Produto
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_home.*

class CategoryFragment: BaseFragment<CategoryViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProducts()
    }

    override fun handleSuccess(value: Any?) {
        Log.w("PRODUTOS", "handleSuccess called with ${value?.javaClass?.name}")
        when(value) {
            is Pageable<*> -> setPage(value)
        }
    }

    override fun handleError(error: Throwable?) {
        Log.e("PRODUTOS", "handleError called", error)
    }

    private fun setPage(pageable: Pageable<*>) {
        pageable.data.forEach {
            val prod = it as Produto
            val adapter = categoryProducts.adapter as ProductAdapter
            adapter.add(prod)
        }
    }

    override fun getViewModelClass(): Class<CategoryViewModel> {
        return CategoryViewModel::class.java
    }

    override fun observeViewModel() {
        viewModel.availableChannels.forEach { observeChannel(it) }
    }

    private fun getProducts() {
        Log.w("PRODUTOS", "getProducts called")
        viewModel.getProducts(1L)
    }

    override fun setupViews(view: View) {
        setupToolbar(view.findViewById(R.id.toolbar), false)
        setupProductList()
    }

    private fun setupProductList() {
        categoryProducts.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ProductAdapter(listener = ::onProductSelectionListener)
        }
    }

    private fun onProductSelectionListener(produto: Produto) {}
}
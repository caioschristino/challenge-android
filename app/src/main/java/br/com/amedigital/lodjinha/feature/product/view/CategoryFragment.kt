package br.com.amedigital.lodjinha.feature.product.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.view.recyclerview.InfiniteScrollListener
import br.com.amedigital.lodjinha.base.view.recyclerview.ProductAdapter
import br.com.amedigital.lodjinha.base.view.ui.BaseFragment
import br.com.amedigital.lodjinha.feature.home.view.HomeFragmentDirections
import br.com.amedigital.lodjinha.feature.product.gateway.CategoryViewModel
import br.com.amedigital.lodjinha.model.Produto
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.toolbar.*

class CategoryFragment: BaseFragment<CategoryViewModel>() {
    private val scrollListener = InfiniteScrollListener(::getNextProducts)
    private val adapter = ProductAdapter(listener = ::onProductSelectionListener)
    private val args: CategoryFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProducts(args.categoryId)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.stopRequests()
    }

    override fun handleSuccess(value: Any?) {
        if (value is Pageable<*>) setProducts(value)
    }

    override fun handleError(error: Throwable?) {
        Log.e("PRODUCTS", "handleError called", error)
    }

    private fun setProducts(pageable: Pageable<*>) {
        adapter.total = pageable.total
        scrollListener.setLoaded()

        if(pageable.data.isEmpty()) {
            adapter.noMoreItems()
        } else {
            pageable.data.forEach {
                adapter.add(it as Produto)
            }
        }
    }

    override fun getViewModelClass(): Class<CategoryViewModel> {
        return CategoryViewModel::class.java
    }

    private fun getNextProducts() {
        viewModel.getMoreProducts()
    }

    override fun setupViews(view: View) {
        setupToolbar(view.findViewById(R.id.toolbar), true)
        setupProductList()
    }

    private fun setupProductList() {
        categoryProducts.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = this@CategoryFragment.adapter
            addOnScrollListener(scrollListener)
        }
    }

    private fun onProductSelectionListener(item: Produto) {
        val action = CategoryFragmentDirections.actionCategoryToProductDetail(item.id)
        NavHostFragment.findNavController(this).navigate(action)
    }
}
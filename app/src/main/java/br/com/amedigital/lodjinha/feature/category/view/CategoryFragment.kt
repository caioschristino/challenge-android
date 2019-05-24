package br.com.amedigital.lodjinha.feature.category.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.view.ui.BaseFragment
import br.com.amedigital.lodjinha.feature.category.gateway.CategoryViewModel

class CategoryFragment: BaseFragment<CategoryViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        setupToolbar(view.findViewById(R.id.toolbar), true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductList()
    }

    private fun setupProductList() {
        /*categoryProducts.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ProductAdapter(items, ::selectionListener)
        }*/
    }

    private fun selectionListener(item: String?) {

    }
}
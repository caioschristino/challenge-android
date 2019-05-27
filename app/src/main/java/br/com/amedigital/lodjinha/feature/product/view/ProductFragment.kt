package br.com.amedigital.lodjinha.feature.product.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.navArgs
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.view.ui.BaseFragment
import br.com.amedigital.lodjinha.feature.product.gateway.ProductDetailsViewModel
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.util.currencyBRL
import br.com.amedigital.lodjinha.util.loadImageIntoView
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.toolbar_product_detail.*

class ProductFragment: BaseFragment<ProductDetailsViewModel>() {
    private val args: ProductFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProduct(args.prodId)
    }


    override fun getViewModelClass(): Class<ProductDetailsViewModel> {
        return ProductDetailsViewModel::class.java
    }

    override fun setupViews(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        setupToolbar(toolbar, true)
    }

    override fun handleError(error: Throwable?) {
        Log.e("DETAILS", "error", error)
    }

    override fun handleSuccess(value: Any?) {
        if(value is Produto) handleProduct(value)
    }

    private fun handleProduct(prod: Produto) {
        loadImageIntoView(prodContainer, prodImg, prod.urlImagem)
        prodName.text = prod.nome
        prodDescription.text = prod.descricao
        prodPriceFrom.text = currencyBRL(prod.precoDe)
        prodPriceTo.text = currencyBRL(prod.precoPor)
        prodHeader.text = prod.categoria.descricao

        reveal(prodName)
        reveal(priceContainer)
        reveal(prodHeader)
        reveal(prodDescription)
    }
}
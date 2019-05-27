package br.com.amedigital.lodjinha.feature.product.view

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.navArgs
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.view.listener.AppBarStateChangeListener
import br.com.amedigital.lodjinha.base.view.ui.BaseFragment
import br.com.amedigital.lodjinha.base.view.ui.DialogConfig
import br.com.amedigital.lodjinha.base.view.util.currencyBRL
import br.com.amedigital.lodjinha.base.view.util.loadImageIntoView
import br.com.amedigital.lodjinha.base.view.util.parseHTML
import br.com.amedigital.lodjinha.feature.product.gateway.ProductDetailsViewModel
import br.com.amedigital.lodjinha.model.Produto
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.toolbar_product_detail.*


class ProductFragment: BaseFragment<ProductDetailsViewModel>() {
    private lateinit var toolbar: Toolbar
    private val args: ProductFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appBarLayout.addOnOffsetChangedListener(ToolbarListener())
        getToolbar()?.title = ""
        placeOrderBtn.setOnClickListener(::placeOrder)
        viewModel.getProduct(args.prodId)
    }


    override fun getViewModelClass(): Class<ProductDetailsViewModel> {
        return ProductDetailsViewModel::class.java
    }

    override fun setupViews(view: View) {
        super.setupViews(view)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.navigationContentDescription = "up"
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
        prodName.text = parseHTML(prod.nome)
        prodDescription.text = parseHTML(prod.descricao)
        prodPriceFrom.text = currencyBRL(prod.precoDe)
        prodPriceTo.text = currencyBRL(prod.precoPor)
        prodHeader.text = parseHTML(prod.nome)
        getToolbar()?.title = prod.categoria.descricao

        reveal(prodName)
        reveal(priceContainer)
        reveal(prodHeader)
        reveal(prodDescription)
    }

    fun placeOrder(view: View) {
        getDialog(DialogConfig(getString(R.string.functionality_unavailable)))?.show()
    }

    inner class ToolbarListener: AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            when {
                state == State.COLLAPSED || state == State.IDLE -> setUpButtonWhite()
                else -> setUpButtonDark()
            }
        }

        private fun setUpButtonDark() {
            getUpButton().setColorFilter(resources.getColor(R.color.greyish_brown), PorterDuff.Mode.SRC_ATOP);
        }

        private fun setUpButtonWhite() {
            getUpButton().setColorFilter(resources.getColor(R.color.white_two), PorterDuff.Mode.SRC_ATOP)
        }

        private fun getUpButton(): ImageView {
            val views = arrayListOf<View>()
            toolbar.findViewsWithText(views, "up", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
            return views[0] as ImageView
        }
    }
}
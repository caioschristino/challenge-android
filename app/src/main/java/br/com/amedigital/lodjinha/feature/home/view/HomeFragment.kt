package br.com.amedigital.lodjinha.feature.home.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.business.dto.Products
import br.com.amedigital.lodjinha.base.view.adapter.CategoryAdapter
import br.com.amedigital.lodjinha.base.view.adapter.ProductAdapter
import br.com.amedigital.lodjinha.base.view.ui.BaseFragment
import br.com.amedigital.lodjinha.feature.home.business.Banners
import br.com.amedigital.lodjinha.feature.home.business.Categories
import br.com.amedigital.lodjinha.feature.home.gateway.HomeViewModel
import br.com.amedigital.lodjinha.model.Banner
import br.com.amedigital.lodjinha.model.Categoria
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.util.isLollipopOrHigher
import com.bumptech.glide.request.RequestOptions
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.SliderTypes.DefaultSliderView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment<HomeViewModel>() {
    companion object {
        const val BANNER_DURATION = 4000L
    }

    private lateinit var toolbar: Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestAllData()
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun setupViews(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        setupToolbar(toolbar, false)
        setupDrawer(toolbar)
        setupSlider()
    }

    private fun setupSlider() {
        slider.apply {
            setPresetTransformer(SliderLayout.Transformer.Accordion)
            setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
            setDuration(BANNER_DURATION)
            stopCyclingWhenTouch(false)
        }
    }

    override fun observeViewModel() {
        viewModel.availableChannels.forEach { observeChannel(it) }
    }

    private fun requestAllData() {
        viewModel.run {
            getBanners()
            getCategories()
            getSalesRank()
        }
    }

    override fun handleSuccess(value: Any?) {
        when(value) {
            is Banners -> setBanners(value.banners)
            is Categories -> setCategories(value.categories)
            is Products -> setSalesRank(value.products)
        }
    }

    private fun setBanners(banners: List<Banner>?) {
        banners?.forEach {addSlider(it.urlImagem, it.linkUrl)}
            .also { reveal(slider)}
    }

    private fun addSlider(urlImagem: String, linkUrl: String) {
        val sliderView = DefaultSliderView(context).apply {
            image(urlImagem)
            setRequestOption(RequestOptions().centerCrop())
            setProgressBarVisible(true)
            setOnSliderClickListener {onSliderClickListener(linkUrl)}
        }
        slider.addSlider(sliderView)
    }

    private fun onSliderClickListener(linkUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
        startActivity(intent)
    }

    private fun setCategories(items: List<Categoria>) {
        categories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(items, ::onCategorySelectionListener)
            hasFixedSize()
        }
        reveal(titleCategories)
    }

    private fun onCategorySelectionListener(item: Categoria) {
        val action = if(isLollipopOrHigher()) R.id.action_home_to_category else R.id.action_home_to_category_compat
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setSalesRank(products: List<Produto>) {
        rankSales.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ProductAdapter(products, ::onProductSelectionListener)
            hasFixedSize()
        }

        reveal(titleRank)
    }

    private fun onProductSelectionListener(item: Produto?) {}

    override fun onStop() {
        super.onStop()
        slider.stopAutoCycle()
    }
}
package br.com.amedigital.lodjinha.feature.home.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.view.adapter.CategoryAdapter
import br.com.amedigital.lodjinha.base.view.adapter.ProductAdapter
import br.com.amedigital.lodjinha.base.view.ui.BaseFragment
import br.com.amedigital.lodjinha.feature.home.business.Banners
import br.com.amedigital.lodjinha.feature.home.business.Categories
import br.com.amedigital.lodjinha.feature.home.gateway.HomeViewModel
import br.com.amedigital.lodjinha.model.Banner
import br.com.amedigital.lodjinha.model.Categoria
import br.com.amedigital.lodjinha.model.Products
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.util.isLollipopOrHigher
import com.bumptech.glide.request.RequestOptions
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.SliderTypes.BaseSliderView
import com.glide.slider.library.SliderTypes.DefaultSliderView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment<HomeViewModel>() {
    private val BANNER_DURATION = 4000L

    private lateinit var toolbar: Toolbar
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            getBanners()
            getCategories()
            getSalesRank()
        }
    }

    override fun onStop() {
        super.onStop()
        slider.stopAutoCycle()
    }

    override fun setupViewModel(clazz: Class<HomeViewModel>) {
        viewModel = ViewModelProviders.of(this)
            .get(clazz)
            .apply {
                availableChannels.forEach { observe(it, this) }
            }
    }

    override fun handleError(error: Throwable?) {
        Log.e("VIEW", "got error", error)
    }

    override fun handleSuccess(value: Any?) {
        when(value) {
            is Banners -> setBanners(value.banners)
            is Categories -> setCategories(value.categories)
            is Products -> setSalesRank(value.products)
            else -> Log.e("VIEW", "could not parse handleSuccess for ${value?.javaClass?.name ?: "null"}")
        }
    }

    override fun doSetups(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        setupViewModel(HomeViewModel::class.java)
        setupToolbar(toolbar, false)
        setupDrawer(toolbar)
        setupSlider()
    }

    private fun observe(channelName: String, viewModel: HomeViewModel) {
        viewModel.observe(channelName,this, Observer { v-> handleResponse(v) })
    }

    private fun setBanners(banners: List<Banner>?) {
        Log.w("VIEW", "setBanners called with ${banners?.size ?: 0} elements")
        banners
            ?.map { Pair(it.urlImagem, it.linkUrl) }
            ?.forEach {
                val (urlImagem, linkUrl) = it
                val sliderView = DefaultSliderView(context).apply {
                    val requestOptions = RequestOptions().centerCrop()
                    image(urlImagem)
                    setRequestOption(requestOptions)
                    setProgressBarVisible(true)
                    setOnSliderClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                        startActivity(intent)
                    }
                }
                slider.addSlider(sliderView)
            }.also {
                reveal(slider)
            }
    }

    private fun setCategories(items: List<Categoria>?) {
        Log.w("VIEW", "setCategories called with ${items?.size ?: 0} elements")

        categories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(items, ::categorySelectionListener)
            hasFixedSize()
        }

        reveal(titleCategories)
    }

    private fun setSalesRank(products: List<Produto>?) {
        Log.w("VIEW", "setSalesRank called with ${products?.size ?: 0} elements")

        rankSales.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ProductAdapter(products, ::productSelectionListener)
            hasFixedSize()
        }

        reveal(titleRank)
    }

    private fun productSelectionListener(item: Produto?) {

    }

    private fun categorySelectionListener(item: Categoria?) {
        val action = if(isLollipopOrHigher()) R.id.action_home_to_category else R.id.action_home_to_category_compat
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setupSlider() {
        slider.apply {
            setPresetTransformer(SliderLayout.Transformer.Accordion)
            setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
            setDuration(BANNER_DURATION)
            stopCyclingWhenTouch(false)
        }
    }
}
package br.com.amedigital.lodjinha.base.view.recyclerview

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.base.view.util.currencyBRL
import br.com.amedigital.lodjinha.base.view.util.parseHTML
import kotlinx.android.synthetic.main.item_product.view.*


class ProductAdapter(items: List<Produto> = emptyList(), listener: (Produto)->Unit):
    Adapter<Produto>(items, listener) {
    private var hasMoreItems: Boolean = true
    var total: Int = 0

    override fun getFactoryMediator(): ViewHolderFactoryMediator<Produto> {
        return Mediator()
    }

    override fun getItemCount(): Int {
        return when {
            hasMoreItems && list.size < total -> list.size + 1
            else -> list.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<Produto>, position: Int) {
        if(position < list.size && list[position] != null) holder.bind(list[position]!!, listener)
    }

    fun noMoreItems() {
        hasMoreItems = false
        notifyDataSetChanged()
    }
}

class Mediator: ViewHolderFactoryMediator<Produto>() {
    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_LOADING = 2
        private const val VIEW_TYPE_ITEM_LAST = 3

    }

    private val mapping : Map<Int, ViewHolderFactory<Produto>>

    init {
        mapping = mapOf(
            VIEW_TYPE_LOADING to ProgressFactory<Produto>(),
            VIEW_TYPE_ITEM to ProductViewHolderFactory(R.layout.item_product),
            VIEW_TYPE_ITEM_LAST to ProductViewHolderFactory(R.layout.item_product_last)
        )
    }

    override fun getViewType(position: Int, list: List<Produto?>): Int {
        return when (position) {
            list.size -> VIEW_TYPE_LOADING
            list.size-1 -> VIEW_TYPE_ITEM_LAST
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun createViewHolderFactory(code: Int): ViewHolderFactory<Produto>? {
        return mapping[code]
    }
}

open class ProductViewHolderFactory(@LayoutRes private val layoutId: Int): ViewHolderFactory<Produto>() {
    override fun create(parent: ViewGroup): ViewHolder<Produto> {
        return ProductViewHolder(inflate(layoutId, parent))
    }
}

class ProductViewHolder(itemView: View): ViewHolder<Produto>(itemView) {
    private val prodImg: ImageView = itemView.prodImg
    private val prodDescription: TextView = itemView.prodDescription
    private val prodPriceFrom: TextView = itemView.prodPriceFrom
    private val prodPriceTo: TextView = itemView.prodPriceTo

    override fun bind(item: Produto, listener: (Produto) -> Unit) {
        item.let {
            loadImage(it.urlImagem, prodImg)

            prodDescription.text = parseHTML(it.descricao)
            animateText(prodDescription)

            prodPriceFrom.text = getString(R.string.price_from, currencyBRL(it.precoDe))
            animateText(prodPriceFrom)

            prodPriceTo.text = getString(R.string.price_to, currencyBRL(it.precoPor))
            animateText(prodPriceTo)

        }
        super.bind(item, listener)
    }
}
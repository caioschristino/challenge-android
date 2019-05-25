package br.com.amedigital.lodjinha.base.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.model.Produto
import br.com.amedigital.lodjinha.util.currencyBRL
import br.com.amedigital.lodjinha.util.parseHTML
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(items: List<Produto> = emptyList(), listener: (Produto)->Unit):
    Adapter<Produto>(R.layout.item_product, items, listener) {

    override fun getViewHolder(view: View): ViewHolder<Produto> {
        return ProductViewHolder(view)
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
}
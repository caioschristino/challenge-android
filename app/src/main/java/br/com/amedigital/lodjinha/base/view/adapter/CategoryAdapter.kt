package br.com.amedigital.lodjinha.base.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.model.Categoria
import br.com.amedigital.lodjinha.util.parseHTML
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(items: List<Categoria> = emptyList(), listener: (Categoria)->Unit): Adapter<Categoria>(
    R.layout.item_category, items, listener) {

    override fun getViewHolder(view: View): ViewHolder<Categoria> {
        return CategoryViewHolder(view)
    }

    class CategoryViewHolder(itemView: View): ViewHolder<Categoria>(itemView) {
        private val categoryImg: ImageView = itemView.categoryImg
        private val categoryName: TextView = itemView.categoryName

        override fun bind(item: Categoria, listener: (Categoria) -> Unit) {
            item.let {
                categoryName.text = parseHTML(it.descricao)
                animateText(categoryName)
                loadImage(it.urlImagem, categoryImg)
            }
            super.bind(item, listener)
        }
    }
}
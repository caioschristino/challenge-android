package br.com.amedigital.lodjinha.base.view.adapter

import android.util.Log
import android.view.View
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.model.Categoria
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(items: List<Categoria>?, listener: (Categoria?)->Unit): Adapter<Categoria>(
    R.layout.item_category, items, listener) {

    override fun getViewHolder(view: View): ViewHolder<Categoria> {
        return CategoryViewHolder(view)
    }

    class CategoryViewHolder(itemView: View): ViewHolder<Categoria>(itemView) {
        val categoryImg = itemView.categoryImg
        val categoryName = itemView.categoryName

        override fun bind(item: Categoria?, listener: (Categoria?) -> Unit) {
            item?.let {
                categoryName.text = it.descricao
                animateText(categoryName)
                loadImage(it.urlImagem, categoryImg)
            }
            super.bind(item, listener)
        }
    }
}
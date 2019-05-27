package br.com.amedigital.lodjinha.feature.about.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.view.ui.AbstractFragment

class AboutFragment: AbstractFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDrawerAndToolbar(view, true)
    }
}
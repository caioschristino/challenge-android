package br.com.amedigital.lodjinha.feature.home.view

import android.os.Bundle
import android.widget.TextView
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.view.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*if(isLollipopOrHigher()) {
            getHeaderTextNavigationView().letterSpacing = 0.6F
        }*/
    }

    private fun getHeaderTextNavigationView(): TextView {
        val view = navigationView.getHeaderView(0)
        return view.findViewById(R.id.drawerHeaderTxt) as TextView
    }
}

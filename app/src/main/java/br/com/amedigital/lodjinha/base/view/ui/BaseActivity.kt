package br.com.amedigital.lodjinha.base.view.ui

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import br.com.amedigital.lodjinha.R
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity: AppCompatActivity() {

    fun resetToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }

    fun setupDrawer(toolbar: Toolbar) {
        val actionToggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        navigationView.itemIconTintList = null
        drawer.addDrawerListener(actionToggle)
        actionToggle.syncState()
    }
}
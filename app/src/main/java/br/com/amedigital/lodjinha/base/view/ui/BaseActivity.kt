package br.com.amedigital.lodjinha.base.view.ui

import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import br.com.amedigital.lodjinha.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity: AppCompatActivity() {

    fun resetToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }

    fun setupDrawer(toolbar: Toolbar, listener: (MenuItem) -> Boolean) {
        val actionToggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        navigationView.itemIconTintList = null
        drawer.addDrawerListener(actionToggle)
        actionToggle.syncState()
        navigationView.setNavigationItemSelectedListener(listener)
    }

    fun closeDrawer() {
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun getNavHostFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    }
}
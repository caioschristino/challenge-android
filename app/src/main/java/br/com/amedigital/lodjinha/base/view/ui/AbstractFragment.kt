package br.com.amedigital.lodjinha.base.view.ui

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import br.com.amedigital.lodjinha.R

abstract class AbstractFragment: Fragment() {
    protected open fun setupViews(view: View) {}

    protected fun setupToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean) {
        (activity as? BaseActivity)?.resetToolbar(toolbar, homeAsUpEnabled)
    }

    protected fun setupDrawer(toolbar: Toolbar) {
        (activity as? BaseActivity)?.setupDrawer(toolbar, ::onNavigationItemSelected)
    }

    protected fun closeDrawer() {
        (activity as? BaseActivity)?.closeDrawer()
    }

    protected fun getToolbar(): ActionBar? {
        return (activity as? BaseActivity)?.getToolbar()
    }

    protected fun getNavHostFragment(): Fragment? {
        return (activity as? BaseActivity)?.getNavHostFragment()
    }

    protected fun setupDrawerAndToolbar(view: View, homeAsUpEnabled: Boolean) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        setupToolbar(toolbar, homeAsUpEnabled)
        setupDrawer(toolbar)
    }

    protected open fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_about -> navigateToAbout()
            R.id.action_home -> navigateToHome()
            else -> closeDrawer()
        }
        return true
    }

    protected open fun navigateToHome() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_homeFragment)
        closeDrawer()
    }

    protected open fun navigateToAbout() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_aboutFragment)
        closeDrawer()
    }

    protected fun getCurrentFragment(): Fragment? {
        val navHostFragment = getNavHostFragment()
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }
}
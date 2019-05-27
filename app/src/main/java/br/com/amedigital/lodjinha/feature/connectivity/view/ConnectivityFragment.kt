package br.com.amedigital.lodjinha.feature.connectivity.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import br.com.amedigital.lodjinha.R
import br.com.amedigital.lodjinha.base.view.ui.AbstractFragment

class ConnectivityFragment: AbstractFragment() {

    private lateinit var listener: ConnectivityManagerReceiverListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = ConnectivityManagerReceiverListener(::connectionAvailable)
        context?.registerReceiver(listener, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_connectivity, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        context?.unregisterReceiver(listener)
    }

    fun connectionAvailable() {
        NavHostFragment.findNavController(this).navigateUp()
    }
}

class ConnectivityManagerReceiverListener(val listener: ()->Unit): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            listener()
        }
    }
}
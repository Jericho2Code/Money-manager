package com.jericho2code.app_finance_manager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.jericho2code.app_finance_manager.application.extensions.gone
import com.jericho2code.app_finance_manager.application.extensions.visible
import com.jericho2code.app_finance_manager.utils.OpenFullScreenListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OpenFullScreenListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setupWithNavController(Navigation.findNavController(this, R.id.nav_host_fragment))
    }

    override fun onScreenOpen() {
        navigation.gone()
    }

    override fun onScreenClose() {
        navigation.visible()
    }

}

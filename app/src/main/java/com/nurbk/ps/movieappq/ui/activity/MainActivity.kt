package com.nurbk.ps.movieappq.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.databinding.ActivityMainBinding
import com.nurbk.ps.movieappq.others.Constants.setUpStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_nav_host_home) as NavHostFragment?

        val navController = navHostFragment!!.navController

        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()


        NavigationUI.setupWithNavController(
            mBinding.toolbar, navController, appBarConfiguration
        )

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> {
                    bg(R.drawable.splash_bg, R.color.material_color_amber_A700)
                }
                else -> {
                    bg(null, R.color.black)
                    mBinding.toolbar.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun bg(bg: Int?, color: Int) {
        mBinding.root.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.colorPrimaryDark,
                null
            )
        )
        bg?.let {
            mBinding.root.setBackgroundResource(bg)
        }
        setUpStatusBar(this, color)
    }
}
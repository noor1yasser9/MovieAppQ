package com.nurbk.ps.movieappq.ui.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.databinding.ActivityMainBinding
import com.nurbk.ps.movieappq.others.Constants.setUpStatusBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


//        mBinding.button2.setOnClickListener {
//            onAlarmManager()
//
//        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_nav_host_home) as NavHostFragment?

        navController = navHostFragment!!.navController

        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()


        NavigationUI.setupWithNavController(
            mBinding.navView,
            navController
        )


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> {
                    bg(R.drawable.splash_bg, R.color.material_color_amber_A700)
                }
                R.id.detailsMovieFragment, R.id.searchFragment -> {
                    mBinding.toolbar.visibility = View.GONE
                    mBinding.navView.isVisible = false
                }
                else -> {
                    bg(null, R.color.black)
                    mBinding.toolbar.visibility = View.VISIBLE
                    mBinding.navView.isVisible = true
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

    override fun onBackPressed() {
        val destination = navController.currentDestination!!
        when (destination.id) {
            R.id.navigation_movie -> {
                finish()
            }
            R.id.splashFragment -> {

            }
            else -> {
                super.onBackPressed()
            }
        }
    }

//    var time = 1000
//    fun onAlarmManager() {
//        val data = SystemClock.elapsedRealtime().toString()
//        val intent = Intent(this, MyReceiver::class.java)
//        intent.putExtra("data", data)
//
//        val calender = Calendar.getInstance()
//        calender.set(Calendar.HOUR_OF_DAY, 27)
//        calender.set(Calendar.MINUTE, 38)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            this,
//            SystemClock.elapsedRealtime().toInt(),
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        alarmManager.set(AlarmManager.RTC, calender.timeInMillis, pendingIntent)
//        time += 1000
//    }

}
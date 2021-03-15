package com.nurbk.ps.movieappq.others

import android.app.Activity
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat

object Constants {


    fun setUpStatusBar(activity: Activity, color: Int) {
        val window: Window = activity.window.also {
            it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        window.statusBarColor = ContextCompat.getColor(activity, color)
    }
}
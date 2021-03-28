package com.nurbk.ps.movieappq.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("ttttttttttt", intent.getStringExtra("data").toString())
    }
}
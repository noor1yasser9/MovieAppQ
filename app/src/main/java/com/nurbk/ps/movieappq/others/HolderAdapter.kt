package com.nurbk.ps.movieappq.others

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object HolderAdapter {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(StringConstants.imageUrl + imageUrl)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("text")
    fun dateFormat(text: TextView, date: String) {
        var format = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())

        val newDate: Date? = format.parse(date)

        format = SimpleDateFormat("yyyy", Locale.getDefault())

        text.text = format.format(newDate!!)
    }

}
package com.nurbk.ps.movieappq.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.nurbk.ps.movieappq.others.StringConstants
import java.text.SimpleDateFormat
import java.util.*

object HolderAdapter {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, imageUrl: String?) {
//        Glide.with(view.context)
//            .load(StringConstants.imageUrl + imageUrl)
//            .into(view)

        Glide
            .with(view.context)
            .load(StringConstants.imageUrl + imageUrl)
//            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(32 )))
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
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
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object HolderAdapter {
    @JvmStatic
    @BindingAdapter("circleImageUrl")
    fun setCircleImageUrl(imageView: ImageView, url: String?) {
        if (url.isNullOrEmpty()) return

        Glide
            .with(imageView.context)
            .load(StringConstants.imageUrl + url)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }


    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide
            .with(view.context)
            .load(StringConstants.imageUrl + imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(32)))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("loadImage2")
    fun loadImage2(view: ImageView, imageUrl: String?) {
        Glide
            .with(view.context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(32)))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("text")
    fun dateFormat(text: TextView, date: String) {
        var format = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())

        try {
            val newDate: Date? = format.parse(date)

            format = SimpleDateFormat("yyyy", Locale.getDefault())

            text.text = format.format(newDate!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @JvmStatic
    @BindingAdapter("singleText")
    fun singleText(text: TextView, isSingle: Boolean) {
        text.setSingleLine()
        text.isSelected = isSingle
    }
}
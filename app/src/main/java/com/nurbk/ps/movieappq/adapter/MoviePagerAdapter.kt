package com.nurbk.ps.movieappq.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.databinding.ItemMovieLargeBinding
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie

class MoviePagerAdapter(context: Context) : PagerAdapter() {
    private var movieItem = emptyList<ResultMovie>()

    fun setItem(movieItem: List<ResultMovie>) {
        this.movieItem = movieItem
    }

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return (movieItem.size)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
         DataBindingUtil.inflate<ItemMovieLargeBinding>(inflater, R.layout.item_movie_large, container, false)
        binding.setVariable(BR.movie, movieItem[position])
        container.addView(binding.root)


        binding.root.setOnClickListener { onMovieItemClick?.invoke(movieItem[position]) }

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


    enum class ITEM_TYPE {
        SMALL,
        LARGE
    }

    /** Item Click Functions **/
    var onMovieItemClick: ((ResultMovie) -> Unit)? = null

}
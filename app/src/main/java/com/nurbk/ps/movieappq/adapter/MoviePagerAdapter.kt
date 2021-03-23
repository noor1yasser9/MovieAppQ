package com.nurbk.ps.movieappq.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.databinding.ItemMovieBinding
import com.nurbk.ps.movieappq.databinding.ItemMovieLargeBinding
import com.nurbk.ps.movieappq.model.Movie.ResultMovie

class MoviePagerAdapter(private val itemType: ITEM_TYPE) : PagerAdapter() {
    var movieItem = emptyList<ResultMovie>()

    fun setItem(movieItem: List<ResultMovie>) {
        this.movieItem = movieItem
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return (movieItem.size)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = if (itemType == ITEM_TYPE.SMALL)
            DataBindingUtil.inflate<ItemMovieBinding>(
                LayoutInflater.from(container.context),
                R.layout.item_movie,
                container,
                false
            ) else
            DataBindingUtil.inflate<ItemMovieLargeBinding>(
                LayoutInflater.from(container.context),
                R.layout.item_movie_large,
                container,
                false)
        binding.setVariable(BR.movie, movieItem[position])
        container.addView(binding.root)


        binding.root.setOnClickListener { onMovieItemClick?.invoke(movieItem[position]) }

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
    override fun getPageWidth(position: Int): Float {
        return if (itemType == ITEM_TYPE.SMALL) 0.29f else 1.0f
    }

    enum class ITEM_TYPE {
        SMALL,
        LARGE
    }

    /** Item Click Functions **/
    var onMovieItemClick: ((ResultMovie) -> Unit)? = null

}
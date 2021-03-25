package com.nurbk.ps.movieappq.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.adapter.MoviePagerAdapter
import com.nurbk.ps.movieappq.databinding.FragmentMovieDetailBinding
import com.nurbk.ps.movieappq.model.creadits.Cast
import com.nurbk.ps.movieappq.model.creadits.Credits
import com.nurbk.ps.movieappq.model.detailsMovie.Details
import com.nurbk.ps.movieappq.model.detailsMovie.Genre
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.model.similar.Similar
import com.nurbk.ps.movieappq.utils.BasicViewPagerTransformation
import com.nurbk.ps.movieappq.utils.ResultResponse
import com.nurbk.ps.movieappq.view.WrapContentViewPager
import com.nurbk.ps.movieappq.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class DetailsMovieFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Genre> {

    private lateinit var mBinding: FragmentMovieDetailBinding
    private val genresAdapter by lazy {
        GenericAdapter(R.layout.item_genres, BR.genres, this)
    }
    private val creditsAdapter by lazy {
        GenericAdapter(
            R.layout.item_credits,
            BR.cast,
            object : GenericAdapter.OnListItemViewClickListener<Cast> {
                override fun onClickItem(itemViewModel: Cast, type: Int) {

                }
            })
    }
    private val similarAdapter by lazy {
        GenericAdapter(
            R.layout.item_movie,
            BR.movie,
            object : GenericAdapter.OnListItemViewClickListener<ResultMovie> {
                override fun onClickItem(itemViewModel: ResultMovie, type: Int) {

                }
            })
    }


    @Inject
    lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMovieDetailBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.getDerailsMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                        //Log.e("OOO","loading")

                    }
                    ResultResponse.Status.LOADING -> {
                        Log.e("OOO", "loading")

                    }
                    ResultResponse.Status.SUCCESS -> {
                        Log.e("OOO", it.data.toString())
                        val data = it.data as Details
                        mBinding.details = data
                        genresAdapter.data = data.genres


                    }
                    ResultResponse.Status.ERROR -> {
                        Log.e("OOO", it.data.toString())

                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getSimilarMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                        //Log.e("OOO","loading")

                    }
                    ResultResponse.Status.LOADING -> {
                        Log.e("OOO", "loading")

                    }
                    ResultResponse.Status.SUCCESS -> {
                        Log.e("OOO", it.data.toString())
                        val data = it.data as Similar
                        mBinding.layoutSimilarMovies.title = "Similar"
                        setupViewLarge(list = data.results,viewPage = mBinding.layoutSimilarMovies.viewPager,
                            goneView =   mBinding.layoutSimilarMovies.shUpcoming)


                    }
                    ResultResponse.Status.ERROR -> {
                        Log.e("OOO", it.data.toString())

                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getRecommendationsMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                        //Log.e("OOO","loading")

                    }
                    ResultResponse.Status.LOADING -> {
                        Log.e("OOO", "loading")

                    }
                    ResultResponse.Status.SUCCESS -> {
                        Log.e("OOO", it.data.toString())
                        val data = it.data as Similar
                        mBinding.layoutRecommendationMovies.title = "Recommended"
                        setupViewLarge(list = data.results,viewPage = mBinding.layoutRecommendationMovies.viewPager,
                            goneView =  mBinding.layoutRecommendationMovies.shUpcoming)


                    }
                    ResultResponse.Status.ERROR -> {
                        Log.e("OOO", it.data.toString())

                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getCreditsMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                        //Log.e("OOO","loading")

                    }
                    ResultResponse.Status.LOADING -> {
                        Log.e("OOO", "loading")


                    }
                    ResultResponse.Status.SUCCESS -> {
                        Log.e("OOO", it.data.toString())
                        val credits = it.data as Credits
                        creditsAdapter.data = credits.cast


                    }
                    ResultResponse.Status.ERROR -> {
                        Log.e("OOO", it.data.toString())

                    }
                }
            }
        }


        mBinding.rcCredits.apply {
            adapter = creditsAdapter
        }
        mBinding.rcGenres.apply {
            adapter = genresAdapter

        }

    }

    override fun onClickItem(genre: Genre, type: Int) {

    }
    private fun setupViewLarge(
        list: List<ResultMovie>,
        viewPage: WrapContentViewPager,
       goneView: View
    ) {
        goneView.isVisible = false
        MoviePagerAdapter( MoviePagerAdapter.ITEM_TYPE.SMALL).also { adapters ->
            adapters.setItem(list)
            viewPage.apply {
                adapter = adapters
                pageMargin = 60

            }
        }
    }


}
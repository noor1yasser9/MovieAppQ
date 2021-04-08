package com.nurbk.ps.movieappq.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.adapter.MoviePagerAdapter
import com.nurbk.ps.movieappq.databinding.FragmentMovieDetailBinding
import com.nurbk.ps.movieappq.model.creadits.Cast
import com.nurbk.ps.movieappq.model.creadits.Credits
import com.nurbk.ps.movieappq.model.detailsMovie.Details
import com.nurbk.ps.movieappq.model.detailsMovie.Genre
import com.nurbk.ps.movieappq.model.detailsMovie.ProductionCompany
import com.nurbk.ps.movieappq.model.image.Images
import com.nurbk.ps.movieappq.model.image.Poster
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.model.similar.Similar
import com.nurbk.ps.movieappq.model.trailer.Result
import com.nurbk.ps.movieappq.model.trailer.Trailer
import com.nurbk.ps.movieappq.utils.ResultResponse
import com.nurbk.ps.movieappq.view.WrapContentViewPager
import com.nurbk.ps.movieappq.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class DetailsMovieFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Genre> {

    private lateinit var mBinding: FragmentMovieDetailBinding
    private val genresAdapter by lazy {
        GenericAdapter(R.layout.item_genres, BR.genres, this)
    }

    private var bundle: Bundle? = null

    @Inject
    lateinit var viewModel: DetailViewModel

    private lateinit var details: Details
    private lateinit var resultMovie: ResultMovie
    private val creditsAdapter by lazy {
        GenericAdapter(
            R.layout.item_credits,
            BR.cast,
            object : GenericAdapter.OnListItemViewClickListener<Cast> {
                override fun onClickItem(itemViewModel: Cast, type: Int) {

                }
            })
    }
    private val videoAdapter by lazy {
        GenericAdapter(
            R.layout.item_videos,
            BR.item,
            object : GenericAdapter.OnListItemViewClickListener<Result> {
                override fun onClickItem(itemViewModel: Result, type: Int) {
                    playVideo(itemViewModel)
                }

            })
    }
    private val imagesAdapter by lazy {
        GenericAdapter(
            R.layout.item_image_movie,
            BR.poster,
            object : GenericAdapter.OnListItemViewClickListener<Poster> {
                override fun onClickItem(itemViewModel: Poster, type: Int) {

                }

            })
    }
    private val productionAdapter by lazy {
        GenericAdapter(
            R.layout.item_production_company,
            BR.production,
            object : GenericAdapter.OnListItemViewClickListener<ProductionCompany> {
                override fun onClickItem(itemViewModel: ProductionCompany, type: Int) {

                }

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMovieDetailBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        bundle = arguments
        resultMovie = bundle!!.getParcelable("details")!!
        viewModel.getIfExists(resultMovie.id)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.getDerailsMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                    }
                    ResultResponse.Status.LOADING -> {
                    }
                    ResultResponse.Status.SUCCESS -> {
                        val data = it.data as Details
                        details = data
                        mBinding.details = data
                        genresAdapter.data = data.genres
                        genresAdapter.notifyDataSetChanged()
                        productionAdapter.data = data.productionCompanies
                        mBinding.rvProduction.shUpcoming.isVisible = false
                        mBinding.rvProduction.recyclerView.adapter = productionAdapter
                        mBinding.rvProduction.title = "Production"
                        mBinding.rvProduction.visibility =
                            if (data.productionCompanies.isEmpty()) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                    }
                    ResultResponse.Status.ERROR -> {
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getSimilarMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                    }
                    ResultResponse.Status.LOADING -> {
                    }
                    ResultResponse.Status.SUCCESS -> {
                        val data = it.data as Similar
                        mBinding.layoutSimilarMovies.visibility = if (data.results.isEmpty()) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                        mBinding.layoutSimilarMovies.title = "Similar"
                        setupViewLarge(
                            list = data.results, viewPage = mBinding.layoutSimilarMovies.viewPager,
                            goneView = mBinding.layoutSimilarMovies.shUpcoming
                        )


                    }
                    ResultResponse.Status.ERROR -> {
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getRecommendationsMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                    }
                    ResultResponse.Status.LOADING -> {
                    }
                    ResultResponse.Status.SUCCESS -> {
                        val data = it.data as Similar
                        mBinding.layoutRecommendationMovies.visibility =
                            if (data.results.isEmpty()) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                        mBinding.layoutRecommendationMovies.title = "Recommended"
                        setupViewLarge(
                            list = data.results,
                            viewPage = mBinding.layoutRecommendationMovies.viewPager,
                            goneView = mBinding.layoutRecommendationMovies.shUpcoming
                        )


                    }
                    ResultResponse.Status.ERROR -> {
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getCreditsMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                    }
                    ResultResponse.Status.LOADING -> {
                    }
                    ResultResponse.Status.SUCCESS -> {
                        val credits = it.data as Credits
                        try {
                            creditsAdapter.data = credits.cast
                            creditsAdapter.notifyDataSetChanged()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }
                    ResultResponse.Status.ERROR -> {
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getMovieIsExistsLiveData().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                    }
                    ResultResponse.Status.LOADING -> {
                    }
                    ResultResponse.Status.SUCCESS -> {
                        val data = it.data as Boolean
                        if (data) {
                            mBinding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                        } else {
                            mBinding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        }
                        mBinding.btnFavorite.setOnClickListener {
                            if (data) {
                                viewModel.deleteMovie(resultMovie.id)
                                mBinding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            } else {
                                viewModel.insertMovie(resultMovie)
                                mBinding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }
                            viewModel.getIfExists(resultMovie.id)
                        }
                    }
                    ResultResponse.Status.ERROR -> {
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getVideosMovie().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                    }
                    ResultResponse.Status.LOADING -> {
                    }
                    ResultResponse.Status.SUCCESS -> {
                        val data = it.data as Trailer
                        mBinding.trVideo.title = "Trailers"
                        mBinding.trVideo.shUpcoming.isVisible = false
                        videoAdapter.data = data.results
                        mBinding.trVideo.visibility = if (data.results.isEmpty()) {
                            View.GONE
                        } else {
                            mBinding.btnTrailer.setOnClickListener {
                                playVideo(data.results[0])
                            }
                            View.VISIBLE
                        }
                    }

                    ResultResponse.Status.ERROR -> {
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getImagesLiveData().collect {
                when (it.status) {
                    ResultResponse.Status.EMPTY -> {
                    }
                    ResultResponse.Status.LOADING -> {
                    }
                    ResultResponse.Status.SUCCESS -> {
                        val data = it.data as Images
                        mBinding.rcImage.title = "Images"
                        mBinding.rcImage.shUpcoming.isVisible = false
                        imagesAdapter.data = data.posters
                        mBinding.rcImage.recyclerView.adapter = imagesAdapter
                        mBinding.rcImage.visibility = if (data.backdrops.isEmpty()) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                    }

                    ResultResponse.Status.ERROR -> {
                    }
                }
            }
        }
        mBinding.trVideo.recyclerView.apply {
            adapter = videoAdapter
        }

        mBinding.rcCredits.apply {
            adapter = creditsAdapter
        }
        mBinding.rcGenres.apply {
            adapter = genresAdapter

        }

        visibility()
    }

    private fun visibility() {
        mBinding.rcImage.imageButtonMore.isVisible = false
        mBinding.trVideo.imageButtonMore.isVisible = false
        mBinding.rvProduction.imageButtonMore.isVisible = false
        mBinding.layoutRecommendationMovies.imageButtonMore.isVisible = false
        mBinding.layoutSimilarMovies.imageButtonMore.isVisible = false
    }

    override fun onClickItem(genre: Genre, type: Int) {

    }

    private fun setupViewLarge(
        list: List<ResultMovie>,
        viewPage: WrapContentViewPager,
        goneView: View
    ) {
        goneView.isVisible = false
        MoviePagerAdapter(MoviePagerAdapter.ITEM_TYPE.SMALL).also { adapters ->
            adapters.onMovieItemClick = ::onMovieItemClick
            adapters.setItem(list)
            viewPage.apply {
                adapter = adapters
                pageMargin = 60

            }
        }
    }


    private fun onMovieItemClick(movieItem: ResultMovie) {
        viewModel.getDetailsMovie(movieItem.id.toString())
        val data = Bundle()
        data.putString("id", details.id.toString())
        data.putParcelable("details", movieItem)
        findNavController().navigate(R.id.action_detailsMovieFragment_self, data)
    }

    override fun onDestroy() {
        bundle?.let {
            it.getString("id")?.let {
                viewModel.getDetailsMovie(it)
            }
        }
        super.onDestroy()
    }

    private fun playVideo(item: Result) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://${item.key}"))
        startActivity(intent)
    }

}
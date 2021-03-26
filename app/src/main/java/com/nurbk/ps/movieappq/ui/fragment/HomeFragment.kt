package com.nurbk.ps.movieappq.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.adapter.MoviePagerAdapter
import com.nurbk.ps.movieappq.databinding.FragmentHomeBinding
import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.utils.BasicViewPagerTransformation
import com.nurbk.ps.movieappq.utils.MemberItemDecoration
import com.nurbk.ps.movieappq.utils.ResultResponse
import com.nurbk.ps.movieappq.view.WrapContentViewPager
import com.nurbk.ps.movieappq.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<ResultMovie> {

    private lateinit var mBinding: FragmentHomeBinding


    @Inject
    lateinit var viewModel: HomeViewModel

    private val movieAdapter by lazy {
        GenericAdapter(R.layout.item_movie_rec, BR.movie, this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupClickListeners()
    }


    private fun setupViewLarge(
        list: List<ResultMovie>,
        viewPage: WrapContentViewPager,
        type: MoviePagerAdapter.ITEM_TYPE, goneView: View
    ) {
        goneView.isVisible = false
        MoviePagerAdapter(type).also { adapters ->
            adapters.setItem(list)
            adapters.onMovieItemClick = ::onMovieItemClick
            viewPage.apply {
                adapter = adapters
                pageMargin = 60
                if (type == MoviePagerAdapter.ITEM_TYPE.LARGE) {
                    setPageTransformer(false, BasicViewPagerTransformation())
                    currentItem = list.size / 2
                }
            }
        }
    }


    private fun setupViewModel() {

        lifecycleScope.launchWhenStarted {
            viewModel.getNewMovieLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        ResultResponse.Status.LOADING -> {
                            Log.e("ooo", "LOADING")

                        }
                        ResultResponse.Status.SUCCESS -> {

                            val data = it.data as NewPlaying
                            Log.e("ooo", data.toString())
                            setupViewLarge(
                                data.results,
                                mBinding.viewPagerNowPlayingMovies,
                                MoviePagerAdapter.ITEM_TYPE.LARGE,
                                mBinding.shUpcomingPager
                            )
                        }
                        ResultResponse.Status.ERROR -> {
                            Log.e("ooo", "ERROR")

                        }
                        else -> {
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getMovieTopLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        ResultResponse.Status.LOADING -> {
                        }
                        ResultResponse.Status.SUCCESS -> {
                            val data = it.data as NewPlaying
                            setupViewLarge(
                                data.results,
                                mBinding.layoutTopMovies.viewPager,
                                MoviePagerAdapter.ITEM_TYPE.SMALL,
                                mBinding.layoutTopMovies.shUpcoming
                            )
                            mBinding.layoutTopMovies.imageButtonMore.setOnClickListener {
                                navToSeeAll("top_rated", "Top rated")
                            }
                            mBinding.layoutTopMovies.title = "Top Rated"
                        }
                        ResultResponse.Status.ERROR -> {
                        }
                        else -> {
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getUpcomingMovieLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        ResultResponse.Status.LOADING -> {
                        }
                        ResultResponse.Status.SUCCESS -> {
                            val data = it.data as NewPlaying
                            setupViewLarge(
                                data.results,
                                mBinding.layoutUpComingMoviesUp.viewPager,
                                MoviePagerAdapter.ITEM_TYPE.SMALL,
                                mBinding.layoutUpComingMoviesUp.shUpcoming
                            )
                            mBinding.layoutUpComingMoviesUp.imageButtonMore.setOnClickListener {
                                navToSeeAll("upcoming", "Upcoming")
                            }
                            mBinding.layoutUpComingMoviesUp.title = "Upcoming"
                        }
                        ResultResponse.Status.ERROR -> {

                        }
                        else -> {
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getPopularMovieLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        ResultResponse.Status.LOADING -> {
                        }
                        ResultResponse.Status.SUCCESS -> {
                            val data = it.data as NewPlaying
                            movieAdapter.data=data.results


                            mBinding.layoutRcData.imageButtonMore.setOnClickListener {
                                navToSeeAll("popular", "Popular")
                            }
                        }
                        ResultResponse.Status.ERROR -> {
                        }
                        else -> {
                        }
                    }
                }
            }
        }
        rcData()
    }

    private fun rcData() {
        with(mBinding.layoutRcData) {
            shUpcoming.isVisible = false
            title = "Popular"
            this.rvMovie.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(MemberItemDecoration())
            }
        }
    }

    private fun navToSeeAll(type: String, title: String) {
        viewModel.getMovie(type)
        val bundle = Bundle()
        bundle.putString("type", type)
        bundle.putString("title", title)
        findNavController().navigate(R.id.action_homeFragment_to_seeAllFragment, bundle)
    }

    override fun onClickItem(itemViewModel: ResultMovie, type: Int) {
        viewModel.getDetailsMovie(itemViewModel.id.toString())
        findNavController().navigate(R.id.action_homeFragment_to_detailsMovieFragment)

    }


    private fun onMovieItemClick(movieItem: ResultMovie) {
        viewModel.getDetailsMovie(movieItem.id.toString())
        findNavController().navigate(R.id.action_homeFragment_to_detailsMovieFragment)
    }


     fun setupClickListeners() {
        mBinding.imageButtonSearch.setOnClickListener {
            val extras =
                FragmentNavigatorExtras(mBinding.cardViewToolbarContent to mBinding.cardViewToolbarContent.transitionName)
            findNavController().navigate(
                R.id.action_homeFragment_to_searchFragment,
                null,
                null,
                extras
            )
        }


    }
}

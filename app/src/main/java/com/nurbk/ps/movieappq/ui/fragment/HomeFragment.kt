package com.nurbk.ps.movieappq.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.adapter.MoviePagerAdapter
import com.nurbk.ps.movieappq.databinding.FragmentHomeBinding
import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.utils.BasicViewPagerTransformation
import com.nurbk.ps.movieappq.utils.ResultResponse
import com.nurbk.ps.movieappq.view.BaseView
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

//    @Inject
//    lateinit var glide: RequestManager

    @Inject
    lateinit var viewModel: HomeViewModel


    private val movieAdapter by lazy {

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
    }


    private fun setupViewLarge(
        list: List<ResultMovie>,
        viewPage: WrapContentViewPager,
        type: MoviePagerAdapter.ITEM_TYPE, goneView: View
    ) {
        goneView.isVisible = false
        MoviePagerAdapter(type).also { adapters ->
            adapters.setItem(list)
            adapters.notifyDataSetChanged()
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

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false


//    private val onScrollListener = OnScrollListener(isLoading, isLastPage, 0, isScrolling) {
//        viewModel.getAllMovie()
//        isScrolling = false
//    }

    private fun setupViewModel() {

        lifecycleScope.launchWhenStarted {
            viewModel.getMovieLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        ResultResponse.Status.LOADING -> {
                            showProgressBar()
                        }
                        ResultResponse.Status.SUCCESS -> {
                            hideProgressBar()
                            val data = it.data as NewPlaying
                            setupViewLarge(
                                data.results,
                                mBinding.viewPagerNowPlayingMovies,
                                MoviePagerAdapter.ITEM_TYPE.LARGE,
                                mBinding.shUpcomingPager
                            )

                            setupViewLarge(
                                data.results,
                                mBinding.layoutPopularMovies.viewPager,
                                MoviePagerAdapter.ITEM_TYPE.SMALL,
                                mBinding.layoutPopularMovies.shUpcoming
                            )
                            mBinding.layoutPopularMovies.title = "Results"
                        }
                        ResultResponse.Status.ERROR -> {
                            hideProgressBar()
                        }
                    }
                }
            }
        }

    }

    override fun onClickItem(itemViewModel: ResultMovie, type: Int) {

    }

    private fun hideProgressBar() {
        isLoading = false
    }

    private fun showProgressBar() {
        isLoading = true
    }
}

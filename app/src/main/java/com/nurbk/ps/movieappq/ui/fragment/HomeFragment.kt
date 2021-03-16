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
        MoviePagerAdapter(requireContext())
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


    private fun setupView(list: List<Any>) {
        with(mBinding) {
            shUpcomingPager.isVisible = false
            viewPagerNowPlayingMovies.apply {
                adapter = movieAdapter
                pageMargin = 60
                setPageTransformer(false, BasicViewPagerTransformation())
                currentItem = list.size / 2
//                layoutManager = LinearLayoutManager(
//                    requireContext(), LinearLayoutManager.HORIZONTAL, false
//                )
//                addOnScrollListener(onScrollListener)
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
//                            onScrollListener.totalCount = data.totalPages
                            movieAdapter.setItem((data.results))
                            setupView(data.results)
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

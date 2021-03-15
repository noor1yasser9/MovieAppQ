package com.nurbk.ps.movieappq.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.databinding.FragmentHomeBinding
import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.others.OnScrollListener
import com.nurbk.ps.movieappq.others.ResultResponse
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
        GenericAdapter(R.layout.adapter_movie, BR.movie, this)
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
        setupView()
        setupViewModel()
    }


    private fun setupView() {
        with(mBinding) {
            rvMovie.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false
                )
                addOnScrollListener(onScrollListener)
            }


        }
    }

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false


    private val onScrollListener = OnScrollListener(isLoading, isLastPage, 0, isScrolling) {
        viewModel.getAllMovie()
        isScrolling = false
    }

    private fun setupViewModel() {

        lifecycleScope.launchWhenStarted {
            viewModel.getMovieLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        ResultResponse.Status.LOADING -> {
//                            showLoading()
                            showProgressBar()
                        }
                        ResultResponse.Status.SUCCESS -> {
                            hideProgressBar()
                            val data = it.data as NewPlaying
                            onScrollListener.totalCount = data.totalPages
                            movieAdapter.data = (data.results)
//                            movieAdapter.notifyDataSetChanged()
                        }
                        ResultResponse.Status.ERROR -> {
//                            hideProgressBar()
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
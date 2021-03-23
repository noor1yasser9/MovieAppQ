package com.nurbk.ps.movieappq.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.databinding.FragmentSeeAllBinding
import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.utils.OnScrollListener
import com.nurbk.ps.movieappq.utils.ResultResponse
import com.nurbk.ps.movieappq.viewmodel.SeeAllViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SeeAllFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<ResultMovie> {

    private lateinit var mBinding: FragmentSeeAllBinding
    private lateinit var bundle: Bundle

    @Inject
    lateinit var viewModel: SeeAllViewModel
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false


    private val movieAdapter by lazy {
        GenericAdapter(R.layout.adapter_movie, BR.movie, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSeeAllBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        bundle = requireArguments()
        mBinding.title = bundle.getString("title")!!
        return mBinding.root
    }

    private val onScrollListener =
        OnScrollListener(isLoading, isLastPage, 0, isScrolling) {
            viewModel.getMovie(bundle.getString("type")!!)
            isScrolling = false
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcData()
        lifecycleScope.launchWhenStarted {
            viewModel.getMovieMutableLiveData().collect {
                withContext(Dispatchers.Main) {
                    when (it.status) {
                        ResultResponse.Status.LOADING -> {
                            showProgressBar()
                        }
                        ResultResponse.Status.SUCCESS -> {
                            hideProgressBar()
                            val data = it.data as NewPlaying
                            onScrollListener.totalCount = data.totalPages
                            movieAdapter.data = data.results
                            movieAdapter.notifyDataSetChanged()
                        }
                        ResultResponse.Status.ERROR -> {
                            hideProgressBar()
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun rcData() {
        with(mBinding) {
            rcData.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addOnScrollListener(onScrollListener)
            }
        }
    }

    private fun hideProgressBar() {
        isLoading = false
    }

    private fun showProgressBar() {
        isLoading = true
    }

    override fun onDestroy() {
        viewModel.getData().clear()
        super.onDestroy()
    }

    override fun onClickItem(itemViewModel: ResultMovie, type: Int) {

    }
}
package com.nurbk.ps.movieappq.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.adapter.MoviePagerAdapter
import com.nurbk.ps.movieappq.databinding.FragmentSearchBinding
import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.utils.MemberItemDecoration
import com.nurbk.ps.movieappq.utils.OnScrollListener
import com.nurbk.ps.movieappq.utils.ResultResponse
import com.nurbk.ps.movieappq.viewmodel.SeeAllViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_empty.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<ResultMovie> {

    private lateinit var mBinding: FragmentSearchBinding

    @Inject
    lateinit var viewModel: SeeAllViewModel

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false
    private var search = ""
    private val movieAdapter by lazy {
        GenericAdapter(R.layout.item_movie_rec, BR.movie, this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            sharedElementEnterTransition =
                TransitionInflater.from(it).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcData()

        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getSearchMovie(it)
                    search = it
                    mBinding.searchView.hideKeyboard(requireActivity())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.getData().clear()
                    movieAdapter.data = viewModel.getData()
                }
                return true
            }

        })



        viewModel.getSearchMovieMutableLiveData().observe(viewLifecycleOwner) {
            when (it.status) {
                ResultResponse.Status.LOADING -> {
                    showProgressBar()
                }
                ResultResponse.Status.SUCCESS -> {
                    hideProgressBar()
                    val data = it.data as NewPlaying
                    onScrollListener.totalCount = data.totalPages

                    movieAdapter.data = data.results

                    Log.e("ttttttttttt","getSearchMovieMutableLiveData")

                }
                ResultResponse.Status.ERROR -> {
                    hideProgressBar()
                }
                else -> {
                }
            }
        }

    }

    override fun onClickItem(itemViewModel: ResultMovie, type: Int) {

    }

    private val onScrollListener =
        OnScrollListener(isLoading, isLastPage, 0, isScrolling) {
            viewModel.getSearchMovie(mBinding.searchView.query.toString())
            isScrolling = false
        }

    private fun rcData() {
        with(mBinding) {
            this.rcData.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(MemberItemDecoration())
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

    fun View.hideKeyboard(activity: Activity) {
        val view = activity.view.rootView
        val inputManager: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
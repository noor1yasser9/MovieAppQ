package com.nurbk.ps.movieappq.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.databinding.FragmentFavoriteBinding
import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.utils.MemberItemDecoration
import com.nurbk.ps.movieappq.utils.ResultResponse
import com.nurbk.ps.movieappq.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<ResultMovie> {

    private val movieAdapter by lazy {
        GenericAdapter(R.layout.item_movie_rec, BR.movie, this)
    }


    private lateinit var mBinding: FragmentFavoriteBinding

    @Inject
    lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFavoriteBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.getAllMovieLiveData().collect {
                when (it.status) {
                    ResultResponse.Status.LOADING -> {
                    }
                    ResultResponse.Status.SUCCESS -> {
                        val data = it.data as List<ResultMovie>
                        movieAdapter.data = data
                        movieAdapter.notifyDataSetChanged()
                        mBinding.shUpcoming.isVisible = false
                    }
                    ResultResponse.Status.ERROR -> {
                    }
                    else -> {
                    }
                }
            }
        }
        rcData()
    }


    private fun rcData() {
        with(mBinding) {
            this.rcData.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(MemberItemDecoration())
            }
        }
    }

    override fun onClickItem(itemViewModel: ResultMovie, type: Int) {


    }


}
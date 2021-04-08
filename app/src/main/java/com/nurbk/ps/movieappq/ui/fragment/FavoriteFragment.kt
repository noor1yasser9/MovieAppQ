package com.nurbk.ps.movieappq.ui.fragment


import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurbk.ps.movieappq.BR
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.adapter.GenericAdapter
import com.nurbk.ps.movieappq.databinding.FragmentFavoriteBinding
import com.nurbk.ps.movieappq.db.MovieDB
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.utils.MemberItemDecoration
import com.nurbk.ps.movieappq.utils.ResultResponse
import com.nurbk.ps.movieappq.viewmodel.FavoriteViewModel
import com.nurbk.ps.movieappq.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.*
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<ResultMovie> {

    private val movieAdapter by lazy {
        GenericAdapter(R.layout.item_movie_rec, BR.movie, this)
    }


    private lateinit var mBinding: FragmentFavoriteBinding

    @Inject
    lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var viewModelHome: HomeViewModel

    @Inject
    lateinit var movieDB: MovieDB


    private var llScroll: LinearLayout? = null

    private var bitmap: Bitmap? = null
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
        viewModelHome.getDetailsMovie(itemViewModel.id.toString())
        val data = Bundle()
        data.putParcelable("details", itemViewModel)
        findNavController().navigate(R.id.action_navigation_favorite_to_detailsMovieFragment, data)

    }


}
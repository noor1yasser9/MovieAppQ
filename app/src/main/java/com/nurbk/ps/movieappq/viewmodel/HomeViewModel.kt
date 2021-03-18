package com.nurbk.ps.movieappq.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.movieappq.repositories.MoviesRepositories
import com.nurbk.ps.movieappq.utils.ResultResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val moviesRepositories: MoviesRepositories,
    application: Application
) : AndroidViewModel(application) {

    private var page = 1
    fun getTopMovie() {
        viewModelScope.launch {
            moviesRepositories.getTopMovie(page)
            page++
        }
    }

    fun getNewMovie() {
        viewModelScope.launch {
            moviesRepositories.getNewMovie()
        }
    }

    fun getUpcomingMovie() {
        viewModelScope.launch {
            moviesRepositories.getUpcomingMovie(1)
        }
    }

    init {
        getTopMovie()
        getNewMovie()
//        getUpcomingMovie()
    }

    fun getMovieTopLiveData() = moviesRepositories.getMovieTopLiveData()

    fun getNewMovieLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getNewMovieLiveData()

    fun getUpcomingMovieLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getUpcomingMovieLiveData()
}
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

    fun getTopMovie() {
        viewModelScope.launch {
            moviesRepositories.getTopMovie()
        }
    }

    fun getNewMovie() {
        viewModelScope.launch {
            moviesRepositories.getNewMovie()
        }
    }

    fun getUpcomingMovie() {
        viewModelScope.launch {
            moviesRepositories.getUpcomingMovie()
        }
    }

    fun getPopularMovie() {
        viewModelScope.launch {
            moviesRepositories.getPopularMovie()
        }
    }

    init {
        getTopMovie()
        getNewMovie()
        getUpcomingMovie()
        getPopularMovie()
    }

    fun getMovieTopLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getMovieTopLiveData()

    fun getNewMovieLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getNewMovieLiveData()

    fun getUpcomingMovieLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getUpcomingMovieLiveData()

    fun getPopularMovieLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getPopularMovieLiveData()
}
package com.nurbk.ps.movieappq.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.movieappq.repositories.MoviesRepositories
import com.nurbk.ps.movieappq.repositories.SeeAllRepositories
import com.nurbk.ps.movieappq.utils.ResultResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val moviesRepositories: MoviesRepositories,
    val seeRepositories: SeeAllRepositories,
    application: Application
) : AndroidViewModel(application) {

    fun getTopMovie() {
        viewModelScope.launch {
            moviesRepositories.getTopMovie()
        }
    }

    fun getDetailsMovie(id:String) {
        viewModelScope.launch {
            moviesRepositories.getDetailsMovie(id)
        }
    }

    fun getNewMovie() {
        viewModelScope.launch {
            moviesRepositories.getnewMovie()
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


    fun getMovie(type: String) {
        viewModelScope.launch {
            seeRepositories.getMovie(type, 1)
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
        moviesRepositories.getnewMovieLiveData()

    fun getUpcomingMovieLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getUpcomingMovieLiveData()

    fun getPopularMovieLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getPopularMovieLiveData()
}
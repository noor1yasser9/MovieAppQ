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

    private fun getTopMovie() {
        moviesRepositories.getTopMovie()

    }
    fun getDetailsMovie(id: String) {
        moviesRepositories.getDetailsMovie(id)

    }

    private fun getNewMovie() {
        moviesRepositories.getNewMovie()
    }

    private fun getUpcomingMovie() {
        moviesRepositories.getUpcomingMovie()
    }

    private fun getPopularMovie() {
        moviesRepositories.getPopularMovie()
    }


    fun getMovie(type: String) {
            seeRepositories.getMovie(type, 1)
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
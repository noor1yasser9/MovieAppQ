package com.nurbk.ps.movieappq.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.repositories.DatabaseRepository
import com.nurbk.ps.movieappq.repositories.MoviesRepositories
import com.nurbk.ps.movieappq.utils.ResultResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val moviesRepositories: MoviesRepositories,
    val databaseRepository: DatabaseRepository, application: Application
) : AndroidViewModel(application) {
    fun getDerailsMovie() = moviesRepositories.getDetailsLiveData()
    fun getCreditsMovie() = moviesRepositories.getCreditsLiveData()
    fun getImagesLiveData() = moviesRepositories.getImagesLiveData()
    fun getSimilarMovie() = moviesRepositories.getSimilarLiveData()
    fun getVideosMovie() = moviesRepositories.getVideosLiveData()
  //  fun getTrVideo() = moviesRepositories.getTrVideo()
    fun getRecommendationsMovie() = moviesRepositories.getRecommendationsLiveData()


    fun getDetailsMovie(id: String) {
        viewModelScope.launch {
            moviesRepositories.getDetailsMovie(id)
        }
    }

    fun getIfExists(id: Int) {
        databaseRepository.getIfExists(id)
    }

    fun insertMovie(movie: ResultMovie) {
        databaseRepository.insertMovie(movie)
    }

    fun deleteMovie(id: Int) {
        databaseRepository.getDeleteMovie(id)
    }


    fun getMovieDeleteLiveData(): StateFlow<ResultResponse<Any>> = databaseRepository.getMovieDeleteLiveData()
    fun getMovieIsExistsLiveData(): StateFlow<ResultResponse<Any>> = databaseRepository.getMovieIsExistsLiveData()
    fun getMovieInsertLiveData(): StateFlow<ResultResponse<Any>> = databaseRepository.getMovieInsertLiveData()

}
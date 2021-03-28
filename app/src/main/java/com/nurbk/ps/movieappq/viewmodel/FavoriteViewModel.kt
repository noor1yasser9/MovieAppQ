package com.nurbk.ps.movieappq.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.movieappq.repositories.DatabaseRepository
import com.nurbk.ps.movieappq.utils.ResultResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val databaseRepository: DatabaseRepository,
    application: Application
) :
    AndroidViewModel(application) {


    private fun getAllMovie() {
        databaseRepository.getAllMovie()
    }

    init {
        getAllMovie()
    }


    fun getAllMovieLiveData(): StateFlow<ResultResponse<Any>> =
        databaseRepository.getMovieAllLiveData()

}
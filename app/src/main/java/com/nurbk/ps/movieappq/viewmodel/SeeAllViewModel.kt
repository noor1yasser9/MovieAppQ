package com.nurbk.ps.movieappq.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.movieappq.repositories.SeeAllRepositories
import com.nurbk.ps.movieappq.utils.ResultResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    val moviesRepositories: SeeAllRepositories,
    application: Application
) : AndroidViewModel(application) {

    var page = 2
    fun getMovie(type: String) {
        viewModelScope.launch {
            moviesRepositories.getMovie(type, page)
            page++
        }
    }

    fun getMovieMutableLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getMovieMutableLiveData()
}
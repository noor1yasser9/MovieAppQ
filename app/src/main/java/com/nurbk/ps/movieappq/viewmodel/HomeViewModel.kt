package com.nurbk.ps.movieappq.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.movieappq.repositories.MoviesRepositories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val moviesRepositories: MoviesRepositories,
    application: Application
) : AndroidViewModel(application) {

    private var page = 1
    fun getAllMovie() {
        viewModelScope.launch {
            moviesRepositories.getAllNewMovie(page)
            page++
        }
    }

    init {
        getAllMovie()
    }

    fun getMovieLiveData() = moviesRepositories.getMovieLiveData()


}
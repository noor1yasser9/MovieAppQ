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

    private var pageSeeAll = 2
    fun getMovie(type: String) {
        viewModelScope.launch {
            moviesRepositories.getMovie(type, pageSeeAll)
            pageSeeAll++
        }
    }

    fun getMovieMutableLiveData(): StateFlow<ResultResponse<Any>> =
        moviesRepositories.getMovieMutableLiveData()

    fun getData() = moviesRepositories.data


    private var pageSearch = 2
    fun getSearchMovie(query: String) {
        viewModelScope.launch {
            moviesRepositories.getSearchMovie(query, pageSearch)
            pageSearch++
        }
    }

    fun getSearchMovieMutableLiveData() =
        moviesRepositories.getSearchMovieMutableLiveData()


    fun getDataSearch() = moviesRepositories.data


}
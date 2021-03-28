package com.nurbk.ps.movieappq.repositories

import com.nurbk.ps.movieappq.db.MovieDAO
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.network.MoviesInterface
import com.nurbk.ps.movieappq.utils.ResultResponse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(val movieDAO: MovieDAO) {


    private val movieInsertLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))

    fun insertMovie(movie: ResultMovie) {

    }


}
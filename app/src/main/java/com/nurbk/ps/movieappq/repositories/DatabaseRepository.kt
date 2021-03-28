package com.nurbk.ps.movieappq.repositories

import com.nurbk.ps.movieappq.db.MovieDAO
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.network.MoviesInterface
import com.nurbk.ps.movieappq.utils.ResultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(val movieDAO: MovieDAO) {


    private val movieInsertLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val movieAllLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val movieIsExistsLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val movieDeleteLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))

    fun insertMovie(movie: ResultMovie) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = movieDAO.insertMovie(movie)
            if (id > 0)
                movieInsertLiveData.emit(ResultResponse.success(id))
            else
                movieInsertLiveData.emit(ResultResponse.error("Ooops:", ""))
        }
    }


    fun getAllMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            movieAllLiveData.emit(ResultResponse.success(movieDAO.getAllMovie()))
        }
    }

    fun getIfExists(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            movieIsExistsLiveData.emit(ResultResponse.success(movieDAO.exists(id)))
        }
    }

    fun getDeleteMovie(movie: ResultMovie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDeleteLiveData.emit(ResultResponse.success(movieDAO.deleteMovie(movie)))
        }
    }

    fun getMovieAllLiveData(): StateFlow<Any> = movieAllLiveData
    fun getMovieDeleteLiveData(): StateFlow<Any> = movieDeleteLiveData
    fun getMovieIsExistsLiveData(): StateFlow<Any> = movieIsExistsLiveData
    fun getMovieInsertLiveData(): StateFlow<Any> = movieInsertLiveData
}
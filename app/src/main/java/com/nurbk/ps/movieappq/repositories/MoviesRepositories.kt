package com.nurbk.ps.movieappq.repositories


import com.nurbk.ps.movieappq.model.newMovie.ResultMovie
import com.nurbk.ps.movieappq.network.MoviesInterface
import com.nurbk.ps.movieappq.utils.ResultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositories @Inject constructor(val movieInterface: MoviesInterface) {

    private val movieTopMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val newMovieMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val upcomingMovieMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val data = ArrayList<ResultMovie>()
    fun getNewMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getNowPlayingMovie(type = "now_playing", page = 1)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            newMovieMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        newMovieMutableLiveData.emit(ResultResponse.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    newMovieMutableLiveData.emit(ResultResponse.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    newMovieMutableLiveData.emit(ResultResponse.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    fun getTopMovie(page: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getNowPlayingMovie(type = "top_rated", page = page)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            data.addAll(it.results)
                            it.results = data
                            movieTopMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        movieTopMutableLiveData.emit(ResultResponse.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    movieTopMutableLiveData.emit(ResultResponse.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    movieTopMutableLiveData.emit(ResultResponse.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    fun getUpcomingMovie(page: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getNowPlayingMovie(type = "upcoming", page = page)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            data.addAll(it.results)
                            it.results = data
                            upcomingMovieMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        upcomingMovieMutableLiveData.emit(ResultResponse.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    upcomingMovieMutableLiveData.emit(ResultResponse.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    upcomingMovieMutableLiveData.emit(ResultResponse.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    fun getMovieTopLiveData(): StateFlow<ResultResponse<Any>> = movieTopMutableLiveData
    fun getNewMovieLiveData(): StateFlow<ResultResponse<Any>> = newMovieMutableLiveData
    fun getUpcomingMovieLiveData(): StateFlow<ResultResponse<Any>> = upcomingMovieMutableLiveData

}
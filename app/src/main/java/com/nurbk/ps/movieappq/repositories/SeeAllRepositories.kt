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
class SeeAllRepositories @Inject constructor(val movieInterface: MoviesInterface) {

    private val movieMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))

     val data = ArrayList<ResultMovie>()

    private var resultMovie: ResultMovie? = null

    fun getMovie(type: String, page: Int) {
        if (page == 1)
            data.clear()
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getNowPlayingMovie(type = type, page = page)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            data.addAll(it.results)
                            it.results = data
                            movieMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        movieMutableLiveData.emit(ResultResponse.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    movieMutableLiveData.emit(ResultResponse.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    movieMutableLiveData.emit(ResultResponse.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    fun getMovieMutableLiveData(): StateFlow<ResultResponse<Any>> = movieMutableLiveData
}
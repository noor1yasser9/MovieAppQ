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
    private val searchMovieMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))

    val data = ArrayList<ResultMovie>()

    val dataSearch = ArrayList<ResultMovie>()


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
                        movieMutableLiveData.emit(
                            ResultResponse.error(
                                "Ooops: ${response.errorBody()}",
                                response.errorBody()!!
                            )
                        )
                    }
                } catch (e: HttpException) {
                    movieMutableLiveData.emit(ResultResponse.error("Ooops: ${e.message()}", e))

                } catch (t: Throwable) {
                    movieMutableLiveData.emit(ResultResponse.error("Ooops: ${t.message}", t))
                }
            }
        }
    }

    fun getSearchMovie(query: String, page: Int) {
        if (page == 1)
            dataSearch.clear()
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.searchMovie(query = query, page = page)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            data.addAll(it.results)
                            it.results = data
                            searchMovieMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        searchMovieMutableLiveData.emit(
                            ResultResponse.error(
                                "Ooops: ${response.errorBody()}",
                                response.errorBody()!!
                            )
                        )
                    }
                } catch (e: HttpException) {
                    searchMovieMutableLiveData.emit(
                        ResultResponse.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    searchMovieMutableLiveData.emit(ResultResponse.error("Ooops: ${t.message}", t))
                }
            }
        }
    }

    fun getMovieMutableLiveData(): StateFlow<ResultResponse<Any>> = movieMutableLiveData
    fun getSearchMovieMutableLiveData(): StateFlow<ResultResponse<Any>> = searchMovieMutableLiveData
}
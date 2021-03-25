package com.nurbk.ps.movieappq.repositories


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
    private val detailsMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))

    private val newMovieMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))

    private val movieUpcomingMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))


    private val moviePopularMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val creditsMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val similarMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))
    private val recommendationsMutableLiveData: MutableStateFlow<ResultResponse<Any>> =
        MutableStateFlow(ResultResponse.loading(""))


    fun getnewMovie() {
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

    fun getTopMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getNowPlayingMovie(type = "top_rated", page = 1)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
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

    fun getUpcomingMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getNowPlayingMovie(type = "upcoming", page = 1)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            movieUpcomingMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        movieUpcomingMutableLiveData.emit(ResultResponse.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    movieUpcomingMutableLiveData.emit(ResultResponse.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    movieUpcomingMutableLiveData.emit(ResultResponse.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    fun getPopularMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getNowPlayingMovie(type = "popular", page = 1)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            moviePopularMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        movieUpcomingMutableLiveData.emit(ResultResponse.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    moviePopularMutableLiveData.emit(ResultResponse.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    moviePopularMutableLiveData.emit(ResultResponse.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    fun getDetailsMovie(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getDetailsMovie(id = id)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            getCreditsMovie(id)
                            getSimilarMovie(id)
                            getRecommendationsMovie(id)
                            detailsMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        detailsMutableLiveData.emit(
                            ResultResponse.success(
                                "Ooops: ${response.errorBody()}", ""
                            )
                        )
                    }
                } catch (e: HttpException) {
                    detailsMutableLiveData.emit(ResultResponse.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    detailsMutableLiveData.emit(ResultResponse.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    private fun getCreditsMovie(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getCreditsMovie(id = id)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            creditsMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        creditsMutableLiveData.emit(
                            ResultResponse.error(
                                "Ooops: ${response.errorBody()}", ""
                            )
                        )
                    }
                } catch (e: HttpException) {
                    creditsMutableLiveData.emit(ResultResponse.error("Ooops: ${e.message()}", e))

                } catch (t: Throwable) {
                    creditsMutableLiveData.emit(ResultResponse.error("Ooops: ${t.message}", t))
                }
            }
        }
    }

    private fun getSimilarMovie(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getMovieData(id = id, "similar")
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            similarMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        similarMutableLiveData.emit(
                            ResultResponse.error(
                                "Ooops: ${response.errorBody()}", ""
                            )
                        )
                    }
                } catch (e: HttpException) {
                    similarMutableLiveData.emit(ResultResponse.error("Ooops: ${e.message()}", e))

                } catch (t: Throwable) {
                    similarMutableLiveData.emit(ResultResponse.error("Ooops: ${t.message}", t))
                }
            }
        }
    }

    private fun getRecommendationsMovie(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieInterface.getMovieData(id = id,"recommendations")
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            recommendationsMutableLiveData.emit(ResultResponse.success(it))
                        }

                    } else {
                        recommendationsMutableLiveData.emit(
                            ResultResponse.error(
                                "Ooops: ${response.errorBody()}", ""
                            )
                        )
                    }
                } catch (e: HttpException) {
                    recommendationsMutableLiveData.emit(
                        ResultResponse.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    recommendationsMutableLiveData.emit(
                        ResultResponse.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }

    fun getCreditsLiveData(): StateFlow<ResultResponse<Any>> = creditsMutableLiveData
    fun getRecommendationsLiveData(): StateFlow<ResultResponse<Any>> =
        recommendationsMutableLiveData

    fun getSimilarLiveData(): StateFlow<ResultResponse<Any>> = similarMutableLiveData
    fun getDetailsLiveData(): StateFlow<ResultResponse<Any>> = detailsMutableLiveData
    fun getMovieTopLiveData(): StateFlow<ResultResponse<Any>> = movieTopMutableLiveData
    fun getnewMovieLiveData(): StateFlow<ResultResponse<Any>> = newMovieMutableLiveData
    fun getUpcomingMovieLiveData(): StateFlow<ResultResponse<Any>> = movieUpcomingMutableLiveData
    fun getPopularMovieLiveData(): StateFlow<ResultResponse<Any>> = moviePopularMutableLiveData

}
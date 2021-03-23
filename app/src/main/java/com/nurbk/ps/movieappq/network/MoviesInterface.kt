package com.nurbk.ps.movieappq.network

import com.nurbk.ps.movieappq.model.DetailsMovie.DetailsMovie
import com.nurbk.ps.movieappq.model.Movie.Playing
import com.nurbk.ps.movieappq.model.Movie.ResultMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesInterface {

    @GET("movie/{type}")

    suspend fun getNowPlayingMovie(
        @Path("type") type: String,
        @Query("page") page: Int = 1
    ): Response<Playing>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int):
            Response<DetailsMovie>
}
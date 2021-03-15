package com.nurbk.ps.movieappq.network

import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesInterface {

    //Movie
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(@Query("page") page: Int = 1): Response<NewPlaying>
}
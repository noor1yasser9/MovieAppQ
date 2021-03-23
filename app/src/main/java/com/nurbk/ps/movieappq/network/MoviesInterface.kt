package com.nurbk.ps.movieappq.network

import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesInterface {

    @GET("movie/{type}")
    suspend fun getNowPlayingMovie(
        @Path("type") type: String,
        @Query("page") page: Int = 1
    ): Response<NewPlaying>


}
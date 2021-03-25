package com.nurbk.ps.movieappq.network

import com.nurbk.ps.movieappq.model.creadits.Credits
import com.nurbk.ps.movieappq.model.detailsMovie.Details
import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import com.nurbk.ps.movieappq.model.similar.Similar
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

    @GET("movie/{id}")
    suspend fun getDetailsMovie(
        @Path("id") id: String,
    ): Response<Details>
    @GET("movie/{id}/credits")
    suspend fun getCreditsMovie(
        @Path("id") id: String,
    ): Response<Credits>

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovie(
        @Path("id") id: String,
    ): Response<Similar>

   @GET("movie/{id}/recommendations")
    suspend fun getRecommendationsMovie(
        @Path("id") id: String,
    ): Response<Similar>


}
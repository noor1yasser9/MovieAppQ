package com.nurbk.ps.movieappq.network

import com.nurbk.ps.movieappq.model.creadits.Credits
import com.nurbk.ps.movieappq.model.detailsMovie.Details
import com.nurbk.ps.movieappq.model.newMovie.NewPlaying
import com.nurbk.ps.movieappq.model.similar.Similar
import com.nurbk.ps.movieappq.model.trailer.Trailer
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

    @GET("movie/{id}/{movies}")
    suspend fun getMovieData(
        @Path("id") id: String,
        @Path("movies") movie: String
    ): Response<Similar>


    @GET("search/movie")
   suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<NewPlaying>

    @GET("movie/{id}/videos")
    suspend fun getVideoData(
        @Path("id") id: String,
    ): Response<Trailer>

}
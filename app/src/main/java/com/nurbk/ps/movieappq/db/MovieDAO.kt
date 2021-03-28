package com.nurbk.ps.movieappq.db

import androidx.room.*
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(resultMovie: ResultMovie): Long

    @Query("SELECT * FROM ResultMovie")
    suspend fun getAllMovie(): List<ResultMovie>


    @Delete
    suspend fun deleteMovie(resultMovie: ResultMovie): Long

    @Query("SELECT EXISTS (SELECT 1 FROM ResultMovie WHERE id = :id)")
    fun exists(id: Int): Boolean

}
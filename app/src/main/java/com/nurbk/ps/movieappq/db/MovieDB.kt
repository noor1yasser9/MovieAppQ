package com.nurbk.ps.movieappq.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie

@Database(entities = [ResultMovie::class], version = 1, exportSchema = false)
abstract class MovieDB : RoomDatabase() {

    abstract fun movieDao(): MovieDAO
}
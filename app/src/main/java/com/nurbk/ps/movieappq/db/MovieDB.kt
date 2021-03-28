package com.nurbk.ps.movieappq.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie

@TypeConverters(value = [ConverterDB::class])
@Database(entities = [ResultMovie::class], version = 7, exportSchema = false)
abstract class MovieDB : RoomDatabase() {

    abstract fun movieDao(): MovieDAO
}
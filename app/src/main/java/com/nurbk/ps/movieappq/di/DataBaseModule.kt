package com.nurbk.ps.movieappq.di

import android.app.Application
import androidx.room.Room
import com.nurbk.ps.movieappq.db.MovieDAO
import com.nurbk.ps.movieappq.db.MovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDB(application: Application): MovieDB {
        return Room
            .databaseBuilder(application, MovieDB::class.java, "fav_DB1")
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(movieDB: MovieDB): MovieDAO {
        return movieDB.movieDao()
    }

}
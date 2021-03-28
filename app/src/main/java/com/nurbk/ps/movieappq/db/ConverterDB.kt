package com.nurbk.ps.movieappq.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConverterDB {

    @TypeConverter
    fun idSToString(ids: ArrayList<Int>): String {
        return Gson().toJson(ids)
    }

    @TypeConverter
    fun stringToIds(string: String): ArrayList<Int> {
        val typeConverter = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(string, typeConverter)
    }

}
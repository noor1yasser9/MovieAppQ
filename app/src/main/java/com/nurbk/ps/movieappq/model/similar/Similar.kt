package com.nurbk.ps.movieappq.model.similar


import com.google.gson.annotations.SerializedName
import com.nurbk.ps.movieappq.model.newMovie.ResultMovie

data class Similar(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<ResultMovie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)
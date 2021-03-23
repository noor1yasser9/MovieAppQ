package com.nurbk.ps.movieappq.model.Movie


import com.google.gson.annotations.SerializedName

data class Playing(
    @SerializedName("dates")
    var dates: DatesMovie,
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<ResultMovie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)
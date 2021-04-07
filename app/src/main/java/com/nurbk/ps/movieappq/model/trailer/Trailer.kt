package com.nurbk.ps.movieappq.model.trailer


import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("id")
    var id: Int,
    @SerializedName("results")
    var results: List<Result>
)
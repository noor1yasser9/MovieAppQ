package com.nurbk.ps.movieappq.model.Movie


import com.google.gson.annotations.SerializedName

data class DatesMovie(
    @SerializedName("maximum")
    var maximum: String,
    @SerializedName("minimum")
    var minimum: String
)
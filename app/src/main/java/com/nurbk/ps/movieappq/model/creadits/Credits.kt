package com.nurbk.ps.movieappq.model.creadits


import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("cast")
    var cast: List<Cast>,
    @SerializedName("crew")
    var crew: List<Crew>,
    @SerializedName("id")
    var id: Int
)
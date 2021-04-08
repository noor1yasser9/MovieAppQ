package com.nurbk.ps.movieappq.model.image


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("backdrops")
    var backdrops: List<Backdrop>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("posters")
    var posters: List<Poster>
)
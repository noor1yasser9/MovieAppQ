package com.nurbk.ps.movieappq.model.newMovie


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ResultMovie constructor(
    @SerializedName("adult")
    var adult: Boolean = false,
    @SerializedName("backdrop_path")
    var backdropPath: String = "",
    @SerializedName("genre_ids")
    var genreIds: ArrayList<Int>? = arrayListOf(),
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_language")
    var originalLanguage: String = "",
    @SerializedName("original_title")
    var originalTitle: String = "",
    @SerializedName("overview")
    var overview: String = "",
    @SerializedName("popularity")
    var popularity: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String = "",
    @SerializedName("release_date")
    var releaseDate: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("video")
    var video: Boolean = false,
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    var voteCount: Int = 0
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var idRoom: Int? = null

}
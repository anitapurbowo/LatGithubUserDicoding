package com.example.submission1.dataclass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubUser (
    var nama : String,
    var company : String,
    var followers : String,
    var repository : String,
    var image : String,
    var location : String,
    var fav : Int
) : Parcelable
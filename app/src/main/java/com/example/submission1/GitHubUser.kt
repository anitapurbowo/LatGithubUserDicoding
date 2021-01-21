package com.example.submission1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubUser (
    var nama : String,
    var company : String,
    var followers : String,
    var image : String
) : Parcelable
package com.example.submission1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class userFav (
    var cNama: String,
    var cCompany : String,
    var cFollowers : String,
    var cImage : String,
    var cLocation : String
) : Parcelable

package com.example.submission1.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.submission1.dataBase.dataBaseFunc.userColumns.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_NAME)
data class userFav (
    @PrimaryKey(autoGenerate = false) var dbNama: String,
    var dbCompany : String,
    var dbFollowers : String,
    var dbRepository : String,
    var dbImage : String,
    var dbLocation : String
)

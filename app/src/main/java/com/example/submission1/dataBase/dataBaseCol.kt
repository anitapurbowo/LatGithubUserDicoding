package com.example.submission1.dataBase

import android.provider.BaseColumns

internal class dataBaseCol {
    internal class userColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "userfav"
            const val _ID = "_id"
            const val Nama = "dbNama"
            const val Company = "dbCompany"
            const val Followers = "dbFollowers"
            const val Image = "dbImage"
            const val Location = "dbLocation"
        }
    }
}

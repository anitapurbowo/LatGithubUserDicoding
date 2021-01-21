package com.example.submission1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class detUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_user)

        val dataUser = intent.getParcelableExtra<GitHubUser>("kirimData")
        Toast.makeText(this, dataUser?.nama, Toast.LENGTH_LONG).show()
    }
}
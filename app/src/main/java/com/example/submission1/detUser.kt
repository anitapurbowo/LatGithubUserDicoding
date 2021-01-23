package com.example.submission1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_det_user.*

class detUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_user)

        val dataUser = intent.getParcelableExtra<GitHubUser>("kirimData")
        Glide.with(this).load(dataUser?.image).into(cvAvatar)
        tvNama.setText(dataUser?.nama)
        tvCompany.setText(dataUser?.company)
        tvLocation.setText(dataUser?.location)
        tvRepo.setText(dataUser?.repository)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        vPager.adapter = sectionPagerAdapter
        tlFoll.setupWithViewPager(vPager)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.act_lang) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}
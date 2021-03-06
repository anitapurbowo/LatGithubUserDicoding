package com.example.submission1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.submission1.dataclass.GitHubUser
import com.example.submission1.fragment.SectionPagerAdapter
import kotlinx.android.synthetic.main.activity_det_user.*

class detUser : AppCompatActivity() {

    companion object {
        const val KIRIM = "kirimData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_user)

        val dataUser = intent.getParcelableExtra<GitHubUser>(KIRIM)
        Glide
            .with(this)
            .load(dataUser?.image)
            .placeholder(R.drawable.openimage)
            .error(R.drawable.ic_baseline_block_24)
            .into(cvAvatar)
        tvNama.setText(dataUser?.nama)
        tvCompany.setText(dataUser?.company)
        tvLocation.setText(dataUser?.location)
        tvRepo.setText(dataUser?.repository)

        val sectionPagerAdapter =
            SectionPagerAdapter(
                this,
                supportFragmentManager
            )
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
package com.example.submission1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.submission1.reminder.reminderReceiver
import kotlinx.android.synthetic.main.activity_notif_setting.*

class notifSetting : AppCompatActivity() {

    companion object {
        const val PREF = "NitPref"
        const val NOTIF = "RemindNotif"
    }

    private lateinit var remReceiver : reminderReceiver
    private lateinit var mSharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.titleNotif)

        remReceiver = reminderReceiver()
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE)

        switchNotif.isChecked = mSharedPreferences.getBoolean(NOTIF, false)
        switchNotif.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                remReceiver.setRepeatingAlarm(this,reminderReceiver.EXTRA_REMINDER, reminderReceiver.EXTRA_TIME,getString(R.string.reminder))
            } else {
                remReceiver.setOffAlarm(this)
            }
            val spEditor = mSharedPreferences.edit()
            spEditor.putBoolean(NOTIF,isChecked)
            spEditor.apply()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
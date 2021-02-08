package com.example.submission1.reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.submission1.MainActivity
import com.example.submission1.R
import java.util.*

class reminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_REMINDER = "Daily Reminder"
        const val EXTRA_MESSAGE = "reminder"
        const val EXTRA_TYPE = "type"

        const val EXTRA_ID = 100
        const val EXTRA_TIME = "09:00"

    }
    override fun onReceive(p0: Context, p1: Intent) {
        val message = p1.getStringExtra(EXTRA_MESSAGE)
        showAlarmNotification(p0, message, EXTRA_ID)
    }

    private fun showAlarmNotification(context: Context, message: String?, notifId : Int) {
        val channelId = "NIT2X"
        val channelName = "Reminder Channel"

//        val intent = Intent(context, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(
//            context, 0,
//            intent, PendingIntent.FLAG_ONE_SHOT
//        )

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentTitle(EXTRA_REMINDER)
                .setContentText(message)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000,1000,1000,1000,1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    fun setRepeatingAlarm(context : Context, type : String, time : String, message : String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, reminderReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE,message)
        val putExtra = intent.putExtra(EXTRA_TYPE, type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, EXTRA_ID, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, R.string.onAlarm, Toast.LENGTH_SHORT).show()

    }

    fun setOffAlarm(context: Context) {
        val alarmManager : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,reminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, EXTRA_ID, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context,R.string.offAlarm, Toast.LENGTH_LONG).show()
    }
}
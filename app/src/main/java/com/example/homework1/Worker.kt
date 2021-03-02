package com.example.homework1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.work.*
import androidx.work.Worker
import java.util.concurrent.TimeUnit
import kotlin.random.Random


// https://towardsdatascience.com/working-with-android-workmanager-using-kotlin-36167a143579 GUIDE

class Worker(appContext: Context, workerParameters: WorkerParameters) : Worker(appContext, workerParameters) {
    override fun doWork(): Result {
        // val userId = inputData.getInt("userId,
        val message = inputData.getString("message")
        val msgId = inputData.getInt("msgId", 0)
        val checked = inputData.getInt("checked", 0)
        val date = inputData.getString("date")
        val location_x = inputData.getDouble("location_x", 0.0)
        val location_y = inputData.getDouble("location_y", 0.0)

        println("Message WORKER: " + message)
        println("CHECKED: " + checked)
        println("laatitude user: " + latitudeUser)
        println("location_x: " + location_x)
        if (checked == 1) {
            var counter = 1
            while (counter == 1) {
                if (latitudeUser < location_x) {
                    showNofitication(applicationContext, message!!, msgId!!, checked!!, date!!)
                    println("NOTIFICATION SENT")
                    reminderSeen(applicationContext, msgId)     //shows up in listview
                    counter = 0
                }
            }
        }
        return Result.success()
    }



    fun showNofitication(context: Context, message: String, msgId: Int, checked: Int, date: String) {
        val CHANNEL_ID = "BANKING_APP_NOTIFICATION_CHANNEL"
        var notificationId = Random.nextInt(10, 1000) + 5
        // notificationId += Random(notificationId).nextInt(1, 500)
        println("SHOW NOTIFICATION MSG ID : " + msgId)

        var notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(message)
                    .setContentText(date)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(date))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setGroup(CHANNEL_ID)
                    .setNotificationSilent()

            val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Notification channel needed since Android 8
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                        CHANNEL_ID,
                        context.getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = context.getString(R.string.app_name)
                }
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(notificationId, notificationBuilder.build())
            playCustomTune()
    }

    fun reminderSeen(context: Context, msgId: Int){
        var db = DataBaseHandler(context)
        db.messageSeen(msgId, 1)
        db.getDataMessages()
    }

    fun playTune(){
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val track = RingtoneManager.getRingtone(applicationContext, notification)
        track.play()
    }

    fun playCustomTune(){
        val track: MediaPlayer? = MediaPlayer.create(applicationContext, R.raw.audio)
        track?.start()
    }
}


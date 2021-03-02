package com.example.homework1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.homework1.databinding.ActivityNewMessageBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit




class NewMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewMessageBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var messageImage = 0
        val context = this

        binding.btnNewBank.setOnClickListener {
            messageImage = 0
            Toast.makeText(context, "Banking image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnNewShoppingCart.setOnClickListener {
            messageImage = 1
            Toast.makeText(context, "Shopping image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnNewClock.setOnClickListener {
            messageImage = 2
            Toast.makeText(context, "Alarm image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnNewCommute.setOnClickListener {
            messageImage = 3
            Toast.makeText(context, "Commute image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnNewPhone.setOnClickListener {
            messageImage = 4
            Toast.makeText(context, "Phone image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.txtMessageDate.setOnClickListener {
            var reminderCalendar = GregorianCalendar.getInstance()
        }

        binding.btnLocationNew.setOnClickListener{
            //startActivityForResult(intent, START_ACTIVITY_MAP_ACTIVITY_NEW)
            startActivityForResult(
                    Intent(applicationContext, MapActivityNew::class.java), 1
            )
        }


        val reminderCalendar = GregorianCalendar.getInstance()
        var reminderDate = ""
        val dateFormat = "dd.MM.yyyy HH:mm"
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        var message = ""

        binding.btnAddMessage.setOnClickListener {
            if (binding.txtNewMessage.text.toString().isEmpty()) {
                Toast.makeText(context, "Please Fill All Data!", Toast.LENGTH_SHORT).show()
            }
            else if (binding.txtMessageTime.text.toString().isEmpty() || binding.txtMessageDate.text.toString().isEmpty()){
                message = binding.txtNewMessage.text.toString()
                reminderDate = ""
            }
            else{
                var dateString = binding.txtMessageDate.text.toString()
                var timeString = binding.txtMessageTime.text.toString()
                val date = LocalDateTime.parse(dateString + " " + timeString, formatter)

                reminderCalendar.set(Calendar.YEAR, date.year)
                reminderCalendar.set(Calendar.MONTH, date.monthValue - 1)
                reminderCalendar.set(Calendar.DAY_OF_MONTH, date.dayOfMonth)
                reminderCalendar.set(Calendar.HOUR_OF_DAY, date.hour)
                reminderCalendar.set(Calendar.MINUTE, date.minute)
                reminderDate = dateString + " " + timeString
                message = binding.txtNewMessage.text.toString()
                println("REMINDER CALENDAR: " + reminderCalendar.time.toString())
            }

            println(reminderDate)
            val current = LocalDateTime.now()
            val formattedCurrent = current.format(formatter)
            var db = DataBaseHandler(context)
            var messages = Messages(message, latitudeNew, longitudeNew, reminderDate, formattedCurrent, loggedAs, 0, messageImage)

            db.insertMessage(messages)

            startActivity(
                    Intent(applicationContext, MenuActivity::class.java)
            )

            val data = db.readDataMessages()
            var messageId = 0
            for (i in 0 until data.size) {
                if (data[i].creator_id == loggedAs) {
                    messageId = data[i].id_message
                }
            }

            fun setReminderWithWorkManager(
                    context: Context,
                    uid: Int,
                    timeInMillis: Long,
                    message: String,
                    checked: Int,
                    date : String,
                    location_x : Double,
                    location_y : Double

            ){
                val data: Data = workDataOf(
                        "message" to message,
                        "msgId" to uid,
                        "checked" to checked,
                        "date" to date,
                        "location_x" to location_x,
                        "location_y" to location_y
                )
                var minutesFromNow = 0L
                if (timeInMillis.toLong() > System.currentTimeMillis()) {
                    minutesFromNow = timeInMillis.toLong() - System.currentTimeMillis()
                    Toast.makeText(context, "" + minutesFromNow, Toast.LENGTH_SHORT).show()
                }
                if (date == ""){
                    minutesFromNow = 0
                }

                println("MINUTES FROM NOW: " + minutesFromNow)
                val reminderRequest = OneTimeWorkRequestBuilder<Worker>()
                    .setInputData(data)
                    // minutesFromNow first
                    .setInitialDelay(minutesFromNow, TimeUnit.MILLISECONDS)
                    .build()
                WorkManager.getInstance(context).enqueue(reminderRequest)

            }

            var checked = 0
            if (binding.checkBox.isChecked){
                checked = 1
            }
            setReminderWithWorkManager(applicationContext, messageId, reminderCalendar.timeInMillis, message, checked, reminderDate, latitudeNew, longitudeNew)
            latitudeNew = 0.0
            longitudeNew = 0.0
        }
    }
}




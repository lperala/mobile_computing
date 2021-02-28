package com.example.homework1

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.homework1.databinding.ActivityNewMessageBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
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


        binding.btnAddMessage.setOnClickListener {
            if (binding.txtNewMessage.text.toString().isEmpty() || binding.txtMessageTime.text.toString().isEmpty()) {
                Toast.makeText(context, "Please Fill All Data!", Toast.LENGTH_SHORT).show()
            } else {

                val reminderCalendar = GregorianCalendar.getInstance()
                val dateFormat = "dd.MM.yyyy HH:mm"
                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.ofPattern(dateFormat)
                val formattedCurrent = current.format(formatter)

                val date = LocalDateTime.parse(binding.txtMessageDate.text.toString() + " " + binding.txtMessageTime.text.toString(), formatter)

                reminderCalendar.set(Calendar.YEAR, date.year)
                reminderCalendar.set(Calendar.MONTH, date.monthValue - 1)
                reminderCalendar.set(Calendar.DAY_OF_MONTH, date.dayOfMonth)
                reminderCalendar.set(Calendar.HOUR_OF_DAY, date.hour)
                reminderCalendar.set(Calendar.MINUTE, date.minute)

                println("REMINDER CALENDAR: " + reminderCalendar.time)
                println("REMINDER CALENDAR in MILLIS: " + reminderCalendar.timeInMillis)
                println("CURRENT TIME: " + LocalDateTime.now())
                println("CURRENT TIME IN MILLIS: " + System.currentTimeMillis())

                var dbMessages = DataBaseHandler(context)
                var reminderDate = binding.txtMessageDate.text.toString() + " " + binding.txtMessageTime.text.toString()
                var messages = Messages(binding.txtNewMessage.text.toString(), "0", "0", reminderDate, formattedCurrent, loggedAs, 0, messageImage) // IMAGE THINGIES************


                /*
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                val current = LocalDateTime.now().format(formatter)
                val currentMillis =  System.currentTimeMillis()

                println("Current: " + current)
                println("CurrentMillis: " + currentMillis)
                //println("ReminderTime: " + reminderCalendar)
                */
                dbMessages.insertMessage(messages)
                startActivity(
                        Intent(applicationContext, MenuActivity::class.java)
                )


                val data = dbMessages.readDataMessages()
                var messageId = 0
                for (i in 0 until data.size) {
                    if (data[i].creator_id == loggedAs) {
                        messageId = data[i].id_message
                    }
                }
                setReminderWithWorkManager(applicationContext, messageId, reminderCalendar.timeInMillis, binding.txtNewMessage.text.toString())
            }
        }
    }
    fun setReminderWithWorkManager(
            context: Context,
            uid: Int,
            timeInMillis: Long,
            message: String
    ){
        val reminderParameters = Data.Builder()
                .putString("message", message)
                .putInt("uid", uid)
                .build()

        var minutesFromNow = 0L
        if (timeInMillis > System.currentTimeMillis())
            minutesFromNow = timeInMillis - (System.currentTimeMillis())
            println("Miillies from now: " + minutesFromNow)
            println("Minutes from now: " + minutesFromNow * 0.00001667)

        val reminderRequest = OneTimeWorkRequestBuilder<Worker>()
                .setInputData(reminderParameters)
                .setInitialDelay(minutesFromNow, TimeUnit.MILLISECONDS)
                .build()

        WorkManager.getInstance(context).enqueue(reminderRequest)
    }
    }

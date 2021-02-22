package com.example.homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework1.databinding.ActivityMenuBinding
import com.example.homework1.databinding.ActivityMessageEditDeleteBinding

public var globalPos = 0

class MessageEditDeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageEditDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageEditDeleteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val context = this

        val db = DataBaseHandler(context)
        val data = db.readDataMessages()




        var messageImage = 0
        binding.btnEditBank.setOnClickListener{
            messageImage = 0
            Toast.makeText(context, "Banking image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditShoppingCart.setOnClickListener{
            messageImage = 1
            Toast.makeText(context, "Shopping image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditClock.setOnClickListener{
            messageImage = 2
            Toast.makeText(context, "Alarm image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditCommute.setOnClickListener{
            messageImage = 3
            Toast.makeText(context, "Commute image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditPhone.setOnClickListener{
            messageImage = 4
            Toast.makeText(context, "Phone image picked!", Toast.LENGTH_SHORT).show()
        }

        var counter = 0
        var location = 0

        while (counter <= globalPos){
            if (data[counter].creator_id == loggedAs){
                counter += 1
                location = data[counter-1].id_message
                }
            }


        for (i in 0 until data.size)
            if (data[i].id_message == location){
                binding.txtEditOriginalMessage.text = data[i].message
                binding.txtEditOriginalDate.text = data[i].reminder_time
            }



        //Toast.makeText(context, "GLOBALPOS: " + globalPos + " LOCATION: " + location, Toast.LENGTH_SHORT).show()


        binding.btnSaveMessage.setOnClickListener {
            if (binding.txtEditDeleteMessage.text.toString().isEmpty() || binding.txtEditDeleteTime.text.toString().isEmpty() || binding.txtEditDeleteDate.text.toString().isEmpty()){
                Toast.makeText(context, "Please Fill All Data!", Toast.LENGTH_SHORT).show()
            }
            else {
                var messages = Messages(
                    binding.txtEditDeleteMessage.text.toString(),
                    "0",
                    "0",
                    binding.txtEditDeleteDate.text.toString() + " - " + binding.txtEditDeleteTime.text.toString(),
                    "0",
                    loggedAs,
                    0,
                    messageImage
                )
                db.updateDataMessages(messages, location)
                startActivity(
                    Intent(applicationContext, MenuActivity::class.java)
                )
            }
        }

        binding.btnDeleteMessage.setOnClickListener {
            db.deleteDataMessages(location)
            startActivity(
                Intent(applicationContext, MenuActivity::class.java)
            )
        }
    }
}

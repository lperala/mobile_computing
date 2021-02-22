package com.example.homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.example.homework1.databinding.ActivityNewMessageBinding

class NewMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewMessageBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        var messageImage = 0
        val context = this
        binding.btnNewBank.setOnClickListener{
            messageImage = 0
            Toast.makeText(context, "Banking image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnNewShoppingCart.setOnClickListener{
            messageImage = 1
            Toast.makeText(context, "Shopping image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnNewClock.setOnClickListener{
            messageImage = 2
            Toast.makeText(context, "Alarm image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnNewCommute.setOnClickListener{
            messageImage = 3
            Toast.makeText(context, "Commute image picked!", Toast.LENGTH_SHORT).show()
        }

        binding.btnNewPhone.setOnClickListener{
            messageImage = 4
            Toast.makeText(context, "Phone image picked!", Toast.LENGTH_SHORT).show()
        }


        binding.btnAddMessage.setOnClickListener {
            if (binding.txtNewMessage.text.toString().isEmpty() || binding.txtMessageTime.text.toString().isEmpty()){
                Toast.makeText(context, "Please Fill All Data!", Toast.LENGTH_SHORT).show()
            }
            else {
                var dbMessages = DataBaseHandler(context)
                var messages = Messages(binding.txtNewMessage.text.toString(),"0" ,"0" , binding.txtMessageDate.text.toString() + " - " + binding.txtMessageTime.text.toString()
                        ,"0", loggedAs,0, messageImage) // IMAGE THINGIES************

                dbMessages.insertMessage(messages)
                startActivity(
                        Intent(applicationContext, MenuActivity::class.java)
                    )
                }
            }

        }
    }

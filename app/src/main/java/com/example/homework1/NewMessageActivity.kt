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

        val context = this

        binding.btnAddMessage.setOnClickListener {
            if (binding.txtNewMessage.text.toString().isEmpty() || binding.txtMessageTime.text.toString().isEmpty()){
                Toast.makeText(context, "Please Fill All Data!", Toast.LENGTH_SHORT).show()
            }
            else {
                var messages = Messages(binding.txtNewMessage.text.toString(),"0" ,"0" , binding.txtMessageTime.text.toString(),"0", loggedAs,0)
                var dbMessages = DataBaseHandler(context)
                dbMessages.insertMessage(messages)
                startActivity(
                        Intent(applicationContext, MenuActivity::class.java)
                    )
                }
            }

        }
    }

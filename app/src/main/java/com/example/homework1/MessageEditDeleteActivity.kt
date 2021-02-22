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
        //binding.txtEditDeleteMessage.text



        val data = db.readDataMessages()
        var counter = 0
        var location = ""

        while (counter < globalPos){
            if (data[counter].creator_id == loggedAs){
                counter += 1
                location = data[counter].id_message.toString()
                }
            }

        Toast.makeText(context, "GLOBALPOS: " + globalPos + " LOCATION: " + location, Toast.LENGTH_SHORT).show()


        binding.btnSaveMessage.setOnClickListener {

            startActivity(
                Intent(applicationContext, MenuActivity::class.java)
            )
        }

        binding.btnDeleteMessage.setOnClickListener {
            db.deleteDataMessages()
            startActivity(
                Intent(applicationContext, MenuActivity::class.java)
            )
        }
    }
}

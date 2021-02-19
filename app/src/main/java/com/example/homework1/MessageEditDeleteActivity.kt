package com.example.homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework1.databinding.ActivityMenuBinding
import com.example.homework1.databinding.ActivityMessageEditDeleteBinding

class MessageEditDeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageEditDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageEditDeleteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSaveMessage.setOnClickListener {
            startActivity(
                Intent(applicationContext, MenuActivity::class.java)
            )
        }

        binding.btnSaveMessage.setOnClickListener() {}
            startActivity(
                Intent(applicationContext, MenuActivity::class.java)
            )
        }
}

package com.example.homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.homework1.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Shows the user's profile
        binding.btnProfile.setOnClickListener{
            Log.d("Lab", "ProfileActivity Button CLicked")
            startActivity(
                Intent(applicationContext, ProfileActivity::class.java)
            )
        }

        binding.btnLogOut.setOnClickListener{
            Log.d("Lab", "LogOut Button CLicked")
            startActivity(
                Intent(applicationContext, MainActivity::class.java)
            )
        }

        binding.btnMenuHelp.setOnClickListener{
            Log.d("Lab", "LogOut Button CLicked")
            startActivity(
                Intent(applicationContext, MenuHelpActivity::class.java)
            )
        }
    }
}
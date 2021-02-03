package com.example.homework1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.homework1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Continues to the Login Activity
        binding.btnLoginActivity.setOnClickListener{
            Log.d("Lab", "LogActivity Button CLicked")
            startActivity(
                    Intent(applicationContext, LogActivity::class.java)
            )
        }

        // Continues to the Signin Activity
        binding.btnSigninActivity.setOnClickListener{
            Log.d("Lab", "SignActivity Button CLicked")
            startActivity(
                Intent(applicationContext, SignActivity::class.java)
            )
        }

        // Continues to the Signin Activity
        binding.btnMainHelp.setOnClickListener{
            Log.d("Lab", "MainHelp Button CLicked")
            startActivity(
                Intent(applicationContext, MainHelpActivity::class.java)
            )
        }
    }
}

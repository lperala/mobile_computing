package com.example.homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.homework1.databinding.ActivityLogBinding

class LogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val context = this
        val db = DataBaseHandler(context)

        // Prints the contents of the database when  the button is pressed. Used for testing purposes.
        /*
        binding.btnRead.setOnClickListener {
            var data = db.readData()
            binding.txtResult.text = ""
            for (i in 0 until data.size) {
                binding.txtResult.append(", USERNAME: " + data[i].userName + ", PASSWORD: " + data[i].passWord + "\n")
            }
        }
         */

        // Checks if the user input Username and Passwords exists in the database. Continues to MenuActivity if true.
        binding.btnLogin.setOnClickListener{
            var data = db.readData()
            for (i in 0 until data.size){
                if (binding.txtUserName.text.toString() == data[i].userName && binding.txtPassword.text.toString() == data[i].passWord){
                    Toast.makeText(context, "Correct Username And Password!", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(applicationContext, MenuActivity::class.java)
                    )
                }

                else if (i == data.size-1) {
                    Toast.makeText(context, "Wrong Username Or Password!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnLogHelp.setOnClickListener{
            startActivity(
                Intent(applicationContext, LogHelpActivity::class.java)
            )
        }
    }
}


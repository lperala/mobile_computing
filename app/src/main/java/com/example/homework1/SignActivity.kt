package com.example.homework1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework1.databinding.ActivityMainBinding
import com.example.homework1.databinding.ActivitySignBinding

class SignActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val context = this

        binding.btnSign.setOnClickListener {
            if (binding.txtPassword.text.toString() != binding.txtPasswordConfirm.text.toString()){
                Toast.makeText(context, "Passwords Do Not Match!", Toast.LENGTH_SHORT).show()
            }

            else if (binding.txtUserName.text.toString().isNotEmpty() && binding.txtPassword.text.toString().isNotEmpty()
                    && binding.txtPasswordConfirm.text.toString().isNotEmpty()
                    && binding.txtPassword.text.toString() == binding.txtPasswordConfirm.text.toString()) {
                var user = User(binding.txtUserName.text.toString(), binding.txtPassword.text.toString())
                var db = DataBaseHandler(context)
                println("TÄÄLLÄ")
                db.insertData(user)

            }

            else {
                Toast.makeText(context, "Please Fill All Data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}